package main.model;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor(force = true) @EqualsAndHashCode(callSuper = true, of = {"email"})
@ToString(callSuper = true, of = {"name"})
public class Users extends AbstractEntity {
    /**
     * Является ли пользователь модератором (может ли править
     * глобальные настройки сайта и модерировать посты)
     */
    @Column(name = "is_moderator", nullable = false)
    private boolean isModerator;

    // Дата и время регистрации пользователя
    @Column(name = "reg_time", nullable = false)
    private Instant regTime;

    // Имя пользователя
    @Column(nullable = false)
    private String name;

    // E-mail пользователя
    @Column(nullable = false)
    private String email;

    // Хэш пароля пользователя
    @Column(nullable = false)
    private String password;

    // Код для восстановления пароля, может быть NULL
    private String code;

    // Фотография (ссылка на файл), может быть NULL
    @Column(columnDefinition = "TEXT")
    private String photo;

    // Публикации пользователя
    @OneToMany(mappedBy = "author",  orphanRemoval = true)
    private final Set<Posts> posts = new HashSet<>();

    // Публикации, модерируемые пользователем
    @OneToMany(mappedBy = "moderatedBy", orphanRemoval = true)
    private final Set<Posts> moderatedPosts = new HashSet<>();

    // Комментарии пользователя
    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private final Set<PostComments> comments = new HashSet<>();

    // Лайки / дизлайки пользователя
    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private final Set<PostVotes> votes = new HashSet<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public boolean isModerator() {
        return isModerator;
    }

    public void setModerator(boolean moderator) {
        isModerator = moderator;
    }

    public Instant getRegTime() {
        return regTime;
    }

    public void setRegTime(Instant regTime) {
        this.regTime = regTime;
    }

    public Set<Posts> getPosts() {
        return posts;
    }

    public Set<Posts> getModeratedPosts() {
        return moderatedPosts;
    }

    public Set<PostComments> getComments() {
        return comments;
    }

    public Set<PostVotes> getVotes() {
        return votes;
    }
}