package main.model.dto;

import com.fasterxml.jackson.annotation.JsonView;
import main.model.PostComments;
import main.model.Posts;
import main.utils.JsonViews;
import main.utils.TimeAgo;
import org.jsoup.Jsoup;


import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class PostDTO implements Comparable<PostDTO> {

    @JsonView({JsonViews.IdName.class, JsonViews.EntityId.class})
    private int id;

    @JsonView({JsonViews.IdName.class, JsonViews.EntityIdName.class})
    private String title;

    @JsonView(JsonViews.IdName.class)
    private String announce;

    @JsonView(JsonViews.EntityIdName.class)
    private String text;

    @JsonView({JsonViews.IdName.class, JsonViews.EntityIdName.class})
    private String time;

    @JsonView({JsonViews.IdName.class, JsonViews.EntityIdName.class})
    private PostAuthorDTO user;

    @JsonView({JsonViews.IdName.class, JsonViews.EntityIdName.class})
    private int viewCount;

    @JsonView({JsonViews.IdName.class, JsonViews.EntityIdName.class})
    private int commentCount;

    @JsonView({JsonViews.IdName.class, JsonViews.EntityIdName.class})
    private long likeCount;

    @JsonView({JsonViews.IdName.class, JsonViews.EntityIdName.class})
    private long dislikeCount;

    @JsonView(JsonViews.EntityIdName.class)
    private List<String> tags;

    @JsonView(JsonViews.EntityIdName.class)
    private List<PostComments> comments;

    private Instant date;

    public PostDTO(Posts post) {
        this(post, 0, 0);
    }

    public PostDTO(Posts post, long likeCount, long dislikeCount) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.text = post.getText();
        this.announce = Jsoup.parse(post.getText()).text();
        this.time = TimeAgo.toDuration(post.getTime());
        this.user = new PostAuthorDTO(post.getAuthor().getId(), post.getAuthor().getName());
        this.viewCount = post.getViewCount();

        this.commentCount = post.getComments().size();
        this.likeCount = likeCount;
        this.dislikeCount = dislikeCount;

        this.date = post.getTime();
        this.comments = new ArrayList<>();
    }

    @Override
    public int compareTo(PostDTO o) {
        int result = o.getCommentCount() - this.getCommentCount();
        if (result == 0) result = o.getDate().compareTo(this.getDate());
        return result;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAnnounce() {
        return announce;
    }

    public void setAnnounce(String announce) {
        this.announce = announce;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public List<PostComments> getComments() {
        return comments;
    }

    public void setComments(List<PostComments> comments) {
        this.comments = comments;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }
}