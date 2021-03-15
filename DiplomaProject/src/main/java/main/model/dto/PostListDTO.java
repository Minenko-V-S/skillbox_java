package main.model.dto;

import com.fasterxml.jackson.annotation.JsonView;
import main.utils.JsonViews;
import org.springframework.data.domain.Page;


import java.util.List;

public class PostListDTO {

    @JsonView(JsonViews.IdName.class)
    private final long count;

    @JsonView(JsonViews.IdName.class)
    private List<?> posts;

    public PostListDTO(Page<?> posts) {
        this.posts = posts.getContent();
        this.count = posts.getTotalElements();
    }

    public long getCount() {
        return count;
    }

    public List<?> getPosts() {
        return posts;
    }

    public void setPosts(List<?> posts) {
        this.posts = posts;
    }
}
