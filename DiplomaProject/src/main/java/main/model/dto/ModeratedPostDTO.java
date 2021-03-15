package main.model.dto;

import com.fasterxml.jackson.annotation.JsonView;
import main.config.Config;
import main.model.Users;
import main.utils.JsonViews;
import org.jsoup.Jsoup;


import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class ModeratedPostDTO {

    @JsonView(JsonViews.IdName.class)
    private int id;

    @JsonView(JsonViews.IdName.class)
    private String time;

    @JsonView(JsonViews.IdName.class)
    private Users user;

    @JsonView(JsonViews.IdName.class)
    private String title;

    @JsonView(JsonViews.IdName.class)
    private String announce;

    public ModeratedPostDTO(int id, Instant time, Users author, String title, String text) {
        this.id = id;
        this.time = DateTimeFormatter.ofPattern(Config.STRING_MODERATED_POST_DATE_FORMAT)
                .withZone(ZoneId.systemDefault()).format(time);
        this.user = author;
        this.title = title;
        this.announce = Jsoup.parse(text).text();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
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
}
