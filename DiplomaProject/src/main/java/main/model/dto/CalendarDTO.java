package main.model.dto;


import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class CalendarDTO {

    private final List<Integer> years;
    private final Map<String, Long> posts;

    public CalendarDTO(List<Integer> years, Map<String, Long> posts) {
        this.years = years;
        this.posts = posts;
    }

    public List<Integer> getYears() {
        return years;
    }

    public Map<String, Long> getPosts() {
        return posts;
    }
}
