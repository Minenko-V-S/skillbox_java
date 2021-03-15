package main.controller;


import main.services.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//todo
// обрабатывает все запросы /api/auth/*
@RestController
@RequestMapping("/api/auth")
public class ApiAuthController {


    @Autowired
    private UserAuthService userAuthService;


    @GetMapping(value="/check")
    public ResponseEntity<?> checkUserIsAuthorized() {
        return userAuthService.checkUserIsAuthorized();
    }
}
