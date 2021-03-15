package main.model.dto;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.ToString;
import main.model.Tags;
import main.utils.JsonViews;


@ToString(of = {"name", "baseWeight", "weight", "totalPostsWithTag"})
public class TagDTO {
    @JsonView(JsonViews.IdName.class)
    private final String name;

    @JsonView(JsonViews.IdName.class)
    private double weight;
    private double baseWeight;
    private Tags tag;
    private final long totalPostsWithTag;

    public TagDTO(Tags tag, long totalPostsWithTag) {
        this.tag = tag;
        this.name = tag.getName();
        this.totalPostsWithTag = totalPostsWithTag;
        this.weight = 0.0;
    }

    public void setBaseWeight(long totalPosts) {
        this.baseWeight = totalPostsWithTag / (double) totalPosts;
    }

    public void setWeight(double maxWeight) {
        this.weight = baseWeight / maxWeight;
    }

    public String getName() {
        return name;
    }

    public double getWeight() {
        return weight;
    }

    public double getBaseWeight() {
        return baseWeight;
    }

    public void setBaseWeight(double baseWeight) {
        this.baseWeight = baseWeight;
    }

    public Tags getTag() {
        return tag;
    }

    public void setTag(Tags tag) {
        this.tag = tag;
    }

    public long getTotalPostsWithTag() {
        return totalPostsWithTag;
    }
}