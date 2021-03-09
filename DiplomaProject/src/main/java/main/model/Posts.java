package main.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import main.enums.ModerationStatus;



import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "posts")
@Data
@NoArgsConstructor(force = true) @EqualsAndHashCode(callSuper = true, of = {"title", "time"})
@ToString(callSuper = true, of = {"title"})
public class Posts extends AbstractEntity {

    /** Скрыта или активна публикация: 0 или 1 */
    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    /** Статус модерации, по умолчанию значение “NEW” */
    @Enumerated(EnumType.STRING)
    @NotNull @Column(name = "moderation_status", nullable = false)
    private ModerationStatus moderationStatus = ModerationStatus.NEW;

    /** ID пользователя-модератора, принявшего решение, или NULL */
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "moderator_id", referencedColumnName="id")
    private Users moderatedBy;

    /** Автор поста */
    @NotNull
    @ManyToOne(cascade = CascadeType.MERGE, optional = false)
    @JoinColumn(name = "user_id", referencedColumnName="id")
    private Users author;

    /** Дата и время публикации поста */
    @NotNull @Column(nullable = false)
    private Instant time;

    /** Заголовок поста */
    @Column(nullable = false)
    private String title;

    /** Текст поста */
    @Column(columnDefinition = "TEXT", nullable = false)
    private String text;

    /** Количество просмотров поста */
    @Column(name = "view_count", nullable = false)
    private int viewCount;

    /**
     * Теги, которыми отмечен данный пост
     *
     * MERGE: If the parent entity is merged into the persistence context,
     *        the related entity will also be merged.
     *
     * PERSIST: If the parent entity is persisted into the persistence context,
     *          the related entity will also be persisted.
     */
    @NotNull
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(name = "post_tags",
            joinColumns = @JoinColumn(name = "post_id", referencedColumnName="id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName="id"))
    private final Set<Tags> tags = new HashSet<>();

    /** Лайки / дизлайки поста */
    @NotNull
    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, orphanRemoval = true)
    private final Set<PostVotes> votes = new HashSet<>();

    /** Комментарии поста */
    @NotNull
    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, orphanRemoval = true)
    private final Set<PostComments> comments = new HashSet<>();

    public void addTag(@NotNull Tags tag) {
        tags.add(tag);
    }

    public void updateViewCount() {
        this.viewCount++;
    }
}
