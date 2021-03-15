package main.controller;

import com.fasterxml.jackson.annotation.JsonView;
import main.services.TagsService;
import main.utils.JsonViews;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiTagController {
    @Autowired
    TagsService tagsService;

    @GetMapping("/api/tag")
    @JsonView(JsonViews.IdName.class)
    public ResponseEntity<?> getTags(
            @RequestParam(name="query", required = false) String query) {
        return tagsService.getWeightedTags(query);
    }
}