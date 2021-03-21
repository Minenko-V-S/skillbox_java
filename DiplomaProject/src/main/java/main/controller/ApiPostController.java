package main.controller;

import main.enums.PostMode;
import main.repositories.PostsRepository;
import main.services.PostsService;
import main.services.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//ToDo
// обрабатывает все запросы /api/post/*
@RestController
public class ApiPostController {

    @Autowired
    private UserAuthService userAuthService;
    @Autowired
    private PostsRepository postsRepository;
    @Autowired
    private PostsService postsService;

    @GetMapping("/api/post")
    public ResponseEntity<?> getPosts(
            @RequestParam(name = "offset") int offset,
            @RequestParam(name = "limit") int limit,
            @RequestParam(name = "mode") String mode) {

        return postsService.getPosts(offset, limit, mode);
    }
}
