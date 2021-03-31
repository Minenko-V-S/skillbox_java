package main.model.dto;

import lombok.Data;


@Data
public class PostAuthorDTO {
    private int id;
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