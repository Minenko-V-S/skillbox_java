package main.controller;


import main.repositories.PostsRepository;
import main.services.PostsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//ToDo
// обрабатывает все запросы /api/post/*
@RestController
@RequestMapping("/api/post")
public class ApiPostController {



    private PostsRepository postsRepository;
    private PostsService postsService;

    public ApiPostController(PostsRepository postsRepository, PostsService postsService) {
        this.postsRepository = postsRepository;
        this.postsService = postsService;
    }

    @GetMapping("")
    public ResponseEntity<?> getPosts(
            @RequestParam("offset") int offset,
            @RequestParam("limit") int limit,
            @RequestParam ("mode") String mode) {

        return postsService.getPosts(offset, limit, mode);
    }


    @GetMapping("/search")
    public ResponseEntity<?> searchPosts(
            @RequestParam(name = "offset") int offset,
            @RequestParam(name = "limit") int limit,
            @RequestParam(name = "query") String query) {

        return postsService.searchPosts(offset, limit, query);
    }

    @GetMapping("/byDate")
    public ResponseEntity<?> searchByDate(
            @RequestParam(name = "offset") int offset,
            @RequestParam(name = "limit") int limit,
            @RequestParam(name = "date") String date) {

        return postsService.searchByDate(offset, limit, date);
    }

    @GetMapping("/byTag")
    public ResponseEntity<?> searchByTag(
            @RequestParam(name = "offset") int offset,
            @RequestParam(name = "limit") int limit,
            @RequestParam(name = "tag") String tagName) {

        return postsService.searchByTag(offset, limit, tagName);
    }

    @GetMapping("/{ID}")
    public ResponseEntity<?> searchPosts(@PathVariable int id) {
        return postsService.getPost(id);
    }
}
