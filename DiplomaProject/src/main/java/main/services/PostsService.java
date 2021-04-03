package main.services;

import main.config.Config;
import main.enums.PostMode;
import main.model.PostComments;
import main.model.Posts;
import main.model.Tags;
import main.model.dto.PostDTO;
import main.model.dto.PostListDTO;
import main.repositories.PostCommentsRepository;
import main.repositories.PostVotesRepository;
import main.repositories.PostsRepository;
import main.repositories.TagsRepository;
import main.utils.APIResponse;
import main.utils.DateUtils;
import main.utils.OffsetBasedPageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.time.Instant;
import java.util.*;

@Service
public class PostsService {


    private PostsRepository postsRepository;

    private TagsRepository tagsRepository;

    private TagsService tagsService;

    private UserAuthService userAuthService;

    private PostVotesRepository postVotesRepository;

    private PostCommentsRepository postCommentsRepository;

    public PostsService(PostsRepository postsRepository, TagsRepository tagsRepository, TagsService tagsService, UserAuthService userAuthService, PostVotesRepository postVotesRepository, PostCommentsRepository postCommentsRepository) {
        this.postsRepository = postsRepository;
        this.tagsRepository = tagsRepository;
        this.tagsService = tagsService;
        this.userAuthService = userAuthService;
        this.postVotesRepository = postVotesRepository;
        this.postCommentsRepository = postCommentsRepository;
    }




//     GET запрос /api/post.
//	 Метод получения постов со всей сопутствующей информацией для главной страницы и подразделов "Новые",
//     "Самые обсуждаемые", "Лучшие" и "Старые". Метод выводит посты, отсортированные в соответствии с параметром mode.
    public ResponseEntity<?> getPosts(int offset, int limit, String postMode) {
        final Instant now = Instant.now();
        final PostMode mode;

        Sort sort = Sort.by(Sort.Direction.DESC, "time");

        try {
            mode = PostMode.getByName(postMode);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(APIResponse.error(e.getMessage()));
        }

        switch (mode) {
            /* сортировать по дате публикации, выводить сначала старые */
            case EARLY:
                sort = Sort.by(Sort.Direction.ASC, "time");
                break;
            /* сортировать по убыванию количества лайков */
            case BEST:
                sort = Sort.by(Sort.Direction.DESC, "like_count");
                break;
            /* сортировать по убыванию количества комментариев */
            case POPULAR:
                /* сортировать по дате публикации, выводить сначала новые */
            case RECENT:
            default:
                break;
        }


//
//        Pageable pageable = PageRequest.of(offset , limit, sort);
//        Page<PostDTO> posts = postsRepository.findAllPosts(now, pageable);
//        return ResponseEntity.ok(new PostListDTO(posts));


        Pageable pageable = new OffsetBasedPageRequest(offset, limit, sort);
        Page<PostDTO> posts = postsRepository.findAllPosts(now, pageable);

        if (mode == PostMode.POPULAR) {
             List<PostDTO> p = new ArrayList<>(posts.getContent());
            Collections.sort(p);
            posts = new PageImpl<>(p);
        }

        return ResponseEntity.ok(new PostListDTO(posts));
    }



//     GET запрос /api/post/search.
//     Метод возвращает посты, соответствующие поисковому запросу - строке query.
//     В случае, если запрос пустой, метод должен выводить все посты.

    public ResponseEntity<?> searchPosts(int offset, int limit, String query) {
        if (query == null || query.length() < Config.INT_POST_MIN_QUERY_LENGTH)
            return ResponseEntity.badRequest().body(APIResponse.error(Config.STRING_POST_INVALID_QUERY));

        Sort sort = Sort.by(Sort.Direction.DESC, "time");
        Pageable pageable = new OffsetBasedPageRequest(offset, limit, sort);
        Page<PostDTO> posts = postsRepository.findAllPostsByQuery(Instant.now(), query, pageable);

        return ResponseEntity.ok(new PostListDTO(posts));
    }

//       GET запрос /api/post/byDate.
//       Выводит посты за указанную дату, переданную в запросе в параметре date.

    public ResponseEntity<?> searchByDate(int offset, int limit, String date) {
        if (!DateUtils.isValidDate(date))
            return ResponseEntity.badRequest().body(APIResponse.error(Config.STRING_POST_INVALID_DATE));

        Sort sort = Sort.by(Sort.Direction.DESC, "time");
        Pageable pageable = new OffsetBasedPageRequest(offset, limit, sort);
        Page<PostDTO> posts = postsRepository.findAllPostsByDate(Instant.now(), date, pageable);

        return ResponseEntity.ok(new PostListDTO(posts));
    }

//    GET запрос /api/post/byTag
//	  Метод выводит список постов, привязанных к тегу, который был передан методу в качестве параметра tag.
    public ResponseEntity<?> searchByTag(int offset, int limit, String tagName) {
        Tags tag = tagsRepository.findByNameIgnoreCase(tagName);

        if (tag == null)
            return ResponseEntity.badRequest().body(
                    APIResponse.error(String.format(Config.STRING_POST_INVALID_TAG, tagName))
            );

        Sort sort = Sort.by(Sort.Direction.DESC, "time");
        Pageable pageable = new OffsetBasedPageRequest(offset, limit, sort);

        Page<PostDTO> posts = postsRepository.findAllPostsByTag(Instant.now(), tag, pageable);

        return ResponseEntity.ok(new PostListDTO(posts));
    }

//      GET запрос /api/post/{ID}
//      Метод выводит данные конкретного поста для отображения на странице поста, в том числе, список
//      комментариев и тэгов, привязанных к данному посту.
    public ResponseEntity<?> getPost(int id) {
        Optional<Posts> postOptional = postsRepository.findById(id);

        if (postOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    APIResponse.error(String.format(Config.STRING_POST_NOT_FOUND, id))
            );

        Posts post = postOptional.get();

        PostDTO postDTO = new PostDTO(post);

        postDTO.setTime(DateUtils.formatDate(post.getTime(), Config.STRING_NEW_POST_DATE_FORMAT));
        postDTO.setLikeCount(postVotesRepository.findByPostAndValue(post, (byte) 1).size());
        postDTO.setDislikeCount(postVotesRepository.findByPostAndValue(post, (byte) -1).size());
        postDTO.setTags(tagsRepository.findTagNamesByPost(post));

        final List<PostComments> comments = postCommentsRepository.findByPost(post);
        postDTO.setComments(comments);

        // Update view count for requested post
        post.updateViewCount();
        Posts savedPost = postsRepository.save(post);

        if (post.getId() != savedPost.getId())
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

        return ResponseEntity.ok(postDTO);
    }
}

