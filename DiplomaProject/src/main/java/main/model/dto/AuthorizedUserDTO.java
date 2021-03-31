package main.model.dto;


import lombok.Data;
import lombok.ToString;



@Data  @ToString
public class AuthorizedUserDTO {

    private int id;
    private String name;
    private String photo;
    private String email;
    private boolean moderation;
    private long moderationCount;
    private boolean settings;

    public AuthorizedUserDTO(int id, String name, String photo, String email) {
        this.id = id;
        this.name = name;
        this.photo = photo;
        this.email = email;
        this.moderation = false;
        this.moderationCount = 0;
        this.settings = false;
    }

    public void setUserIsModerator(int moderationCount) {
        this.moderation = true;
        this.moderationCount = moderationCount;
        this.settings = true;
    }

    public AuthorizedUserDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isModeration() {
        return moderation;
    }

    public void setModeration(boolean moderation) {
        this.moderation = moderation;
    }

    public long getModerationCount() {
        return moderationCount;
    }

    public void setModerationCount(long moderationCount) {
        this.moderationCount = moderationCount;
    }

    public boolean isSettings() {
        return settings;
    }

    public void setSettings(boolean settings) {
        this.settings = settings;
    }
}