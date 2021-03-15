package main.model.dto;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import main.utils.JsonViews;


@Data
public class PostAuthorDTO {
    @JsonView({JsonViews.Id.class})
    private int id;

    @JsonView({JsonViews.IdName.class, JsonViews.EntityIdName.class})
    private String name;

    public PostAuthorDTO() {
    }

    public PostAuthorDTO(int id, String name) {
        this.id = id;
        this.name = name;
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
}