package main.model.dto;


import org.springframework.data.domain.Page;


import java.util.List;

public class PostListDTO {

    private final long count;
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
