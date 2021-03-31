package main.model.dto;


import main.model.PostComments;
import main.model.Posts;
import main.utils.TimeAgo;
import org.jsoup.Jsoup;


import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


public class PostDTO implements Comparable<PostDTO> {

    private int id;
    private String title;
    private String announce;
    private String text;
    private String time;
    private PostAuthorDTO user;
    private int viewCount;
    private int commentCount;
    private long likeCount;
    private long dislikeCount;
    private List<String> tags;
    private List<PostComments> comments;
    private Instant date;

    public PostDTO(Posts post) {
        this(post, 0, 0);
    }

    public PostDTO(int id, String title, String announce, String text, String time, PostAuthorDTO user, int viewCount, int commentCount, long likeCount, long dislikeCount, List<String> tags, List<PostComments> comments, Instant date) {
        this.id = id;
        this.title = title;
        this.announce = announce;
        this.text = text;
        this.time = time;
        this.user = user;
        this.viewCount = viewCount;
        this.commentCount = commentCount;
        this.likeCount = likeCount;
        this.dislikeCount = dislikeCount;
        this.tags = tags;
        this.comments = comments;
        this.date = date;
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

    public PostAuthorDTO getUser() {
        return user;
    }

    public void setUser(PostAuthorDTO user) {
        this.user = user;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public long getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(long likeCount) {
        this.likeCount = likeCount;
    }

    public long getDislikeCount() {
        return dislikeCount;
    }

    public void setDislikeCount(long dislikeCount) {
        this.dislikeCount = dislikeCount;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}