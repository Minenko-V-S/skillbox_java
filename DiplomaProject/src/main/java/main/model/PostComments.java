package main.model;



import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import main.utils.TimeAgo;


import javax.persistence.*;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "post_comments")
@Data
@EqualsAndHashCode(callSuper = true, of = {"text", "time"})
@ToString(callSuper = true, of = {"text", "user", "time"})
public class PostComments extends AbstractEntity  {
    /**
     * Комментарий, на который оставлен этот комментарий (может быть NULL,
     * если комментарий оставлен просто к посту)
     */
    @ManyToOne
    @JoinColumn(name="parent_id", referencedColumnName = "id")
    private PostComments parentComment;

    @OneToMany(mappedBy = "parentComment", orphanRemoval = true)
    private final Set<PostComments> childComments = new HashSet<>();

    // Автор комментария
    @ManyToOne(cascade = CascadeType.MERGE, optional = false)
    @JoinColumn(name="user_id", referencedColumnName = "id", nullable = false, updatable = false)
    private Users user;

    // Пост, к которому написан комментарий
    @ManyToOne(cascade = CascadeType.MERGE, optional = false)
    @JoinColumn(name="post_id", referencedColumnName = "id", nullable = false, updatable = false)
    private Posts post;

    // Дата и время комментария
    @Column(nullable = false)
    private Instant time = Instant.now();

    // Текст комментария
    @Column(columnDefinition = "TEXT", nullable = false)
    private String text;

    public PostComments() {
    }

    public PostComments(PostComments parentComment, Users user, Posts post, Instant time, String text, String timeAgoTime) {
        this.parentComment = parentComment;
        this.user = user;
        this.post = post;
        this.time = time;
        this.text = text;
        this.timeAgoTime = timeAgoTime;
    }

    @Transient
    @JsonProperty("time")
    private String timeAgoTime;

    public String getTimeAgoTime() {
        return TimeAgo.toDuration(getTime());
    }

    public Instant getTime() {
        return time;
    }

    public PostComments getParentComment() {
        return parentComment;
    }

    public void setParentComment(PostComments parentComment) {
        this.parentComment = parentComment;
    }

    public Set<PostComments> getChildComments() {
        return childComments;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Posts getPost() {
        return post;
    }

    public void setPost(Posts post) {
        this.post = post;
    }

    public void setTime(Instant time) {
        this.time = time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTimeAgoTime(String timeAgoTime) {
        this.timeAgoTime = timeAgoTime;
    }
}
