package main.services;

import main.enums.PostMode;
import main.model.dto.PostDTO;
import main.model.dto.PostListDTO;
import main.repositories.PostsRepository;
import main.repositories.TagsRepository;
import main.utils.APIResponse;
import main.utils.OffsetBasedPageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class PostsService {

    @Autowired
    private PostsRepository postsRepository;
    @Autowired
    private TagsRepository tagsRepository;
    @Autowired
    private TagsService tagsService;
    @Autowired
    private UserAuthService userAuthService;


    public PostsService( UserAuthService userAuthService) {
        this.userAuthService = userAuthService;
    }

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
            // сортировать по дате публикации, выводить сначала старые
            case EARLY:
                sort = Sort.by(Sort.Direction.ASC, "time");
                break;

            // сортировать по убыванию количества лайков
            case BEST:
                sort = Sort.by(Sort.Direction.DESC, "like_count");
                break;

            // сортировать по убыванию количества комментариев
            case POPULAR:
                // сортировать по дате публикации, выводить сначала новые
            case RECENT:
            default:
                break;
        }

        Pageable pageable = new OffsetBasedPageRequest(offset, limit, sort);
        Page<PostDTO> posts = postsRepository.findAllPosts(now, pageable);

        if (mode == PostMode.POPULAR) {
            final List<PostDTO> p = new ArrayList<>(posts.getContent());
            Collections.sort(p);
            posts = new PageImpl<>(p);
        }

        return ResponseEntity.ok(new PostListDTO(posts));
    }
}
