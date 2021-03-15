package main.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Entity
@Table(name = "post_votes")
@Data
@NoArgsConstructor(force = true) @EqualsAndHashCode(callSuper = true, of = {"time", "value"})
@ToString(callSuper = true, of = {"value"})
public class PostVotes extends AbstractEntity {

    public PostVotes(@NotNull Users user, @NotNull Posts post) {
        this.user = user;
        this.post = post;
    }

    public PostVotes(@NotNull Users user, @NotNull Posts post, @NotNull Instant time) {
        this(user, post);
        this.time = time;
    }

    // Тот, кто поставил лайк / дизлайк
    @ManyToOne(cascade = CascadeType.MERGE, optional = false)
    @JoinColumn(name="user_id", referencedColumnName = "id", nullable = false)
    private Users user;

    // Пост, которому поставлен лайк / дизлайк
    @ManyToOne(cascade = CascadeType.MERGE, optional = false)
    @JoinColumn(name="post_id", referencedColumnName = "id", nullable = false)
    private Posts post;

    //Дата и время лайка / дизлайка
    @Column(nullable = false)
    private Instant time = Instant.now();

    //Лайк или дизлайк: 1 или -1
    @Column(nullable = false)
    private byte value;

    public void like() {
        this.value = 1;
    }

    public void dislike() {
        this.value = -1;
    }
}
