package main.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import main.enums.ModerationStatus;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "posts", schema="test")
public class Posts extends AbstractEntity {

    // Скрыта или активна публикация: 0 или 1
    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    // Статус модерации, по умолчанию значение “NEW”
    @Enumerated(EnumType.STRING)
    @Column(name = "moderation_status", nullable = false)
    private ModerationStatus moderationStatus = ModerationStatus.NEW;

    // ID пользователя-модератора, принявшего решение, или NULL
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "moderator_id", referencedColumnName = "id")
    private Users moderatedBy;

    // Автор поста
    @ManyToOne(cascade = CascadeType.MERGE, optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Users author;

    // Дата и время публикации поста
    @NotNull
    @Column(nullable = false)
    private Instant time;

    // Заголовок поста
    @Column(nullable = false)
    private String title;

    // Текст поста
    @Column(columnDefinition = "TEXT", nullable = false)
    private String text;

    // Количество просмотров поста
    @Column(name = "view_count", nullable = false)
    private int viewCount;

    public Posts() {
    }

    public Posts(boolean isActive, ModerationStatus moderationStatus, Users moderatedBy, Users author, @NotNull Instant time, String title, String text, int viewCount) {
        this.isActive = isActive;
        this.moderationStatus = moderationStatus;
        this.moderatedBy = moderatedBy;
        this.author = author;
        this.time = time;
        this.title = title;
        this.text = text;
        this.viewCount = viewCount;
    }

    /**
     * Теги, которыми отмечен данный пост
     * <p>
     * MERGE: If the parent entity is merged into the persistence context,
     * the related entity will also be merged.
     * <p>
     * PERSIST: If the parent entity is persisted into the persistence context,
     * the related entity will also be persisted.
     */
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(name = "post_tags",
            joinColumns = @JoinColumn(name = "post_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id"))
    private final Set<Tags> tags = new HashSet<>();

    // Лайки / дизлайки поста
    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, orphanRemoval = true)
    private final Set<PostVotes> votes = new HashSet<>();

    // Комментарии поста
    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, orphanRemoval = true)
    private final Set<PostComments> comments = new HashSet<>();

    public void addTag(@NotNull Tags tag) {
        tags.add(tag);
    }

    public void updateViewCount() {
        this.viewCount++;
    }

    public Users getAuthor() {
        return author;
    }

    public void setAuthor(Users author) {
        this.author = author;
    }

    public Instant getTime() {
        return time;
    }

    public void setTime(Instant time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public Set<PostComments> getComments() {
        return comments;
    }

    public ModerationStatus getModerationStatus() {
        return moderationStatus;
    }

    public void setModerationStatus(ModerationStatus moderationStatus) {
        this.moderationStatus = moderationStatus;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Users getModeratedBy() {
        return moderatedBy;
    }

    public void setModeratedBy(Users moderatedBy) {
        this.moderatedBy = moderatedBy;
    }

    public Set<Tags> getTags() {
        return tags;
    }

    public Set<PostVotes> getVotes() {
        return votes;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}


