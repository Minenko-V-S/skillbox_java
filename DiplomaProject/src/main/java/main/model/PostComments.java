package main.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import main.utils.JsonViews;
import main.utils.TimeAgo;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "post_comments")
@Data
@NoArgsConstructor(force = true) @EqualsAndHashCode(callSuper = true, of = {"text", "time"})
@ToString(callSuper = true, of = {"text", "user", "time"})
public class PostComments extends AbstractEntity  {
    /**
     * Комментарий, на который оставлен этот комментарий (может быть NULL,
     * если комментарий оставлен просто к посту)
     */
    @ManyToOne
    @JoinColumn(name="parent_id", referencedColumnName = "id")
    private PostComments parentComment;

    @NotNull
    @OneToMany(mappedBy = "parentComment", fetch = FetchType.LAZY, orphanRemoval = true)
    private final Set<PostComments> childComments = new HashSet<>();

    /** Автор комментария */
    @NotNull
    @ManyToOne(cascade = CascadeType.MERGE, optional = false)
    @JoinColumn(name="user_id", referencedColumnName = "id", nullable = false, updatable = false)
    private Users user;

    /** Пост, к которому написан комментарий */
    @NotNull
    @ManyToOne(cascade = CascadeType.MERGE, optional = false)
    @JoinColumn(name="post_id", referencedColumnName = "id", nullable = false, updatable = false)
    private Posts post;

    /** Дата и время комментария */
    @NotNull
    @Column(nullable = false)
    private Instant time = Instant.now();

    /** Текст комментария */
    @Column(columnDefinition = "TEXT", nullable = false)
    private String text;

//    @Transient
//    @JsonProperty("time")
//    private String timeAgoTime;

    public String getTimeAgoTime() {
        return TimeAgo.toDuration(getTime());
    }
}
