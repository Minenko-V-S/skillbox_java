package main.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import main.utils.JsonViews;


@Data
@AllArgsConstructor @NoArgsConstructor
public class SettingsDTO {
    @JsonProperty("MULTIUSER_MODE")
    @JsonView(JsonViews.Name.class)
    private boolean multiuserMode;

    @JsonProperty("POST_PREMODERATION")
    @JsonView(JsonViews.Name.class)
    private boolean postPremoderation;

    @JsonProperty("STATISTICS_IS_PUBLIC")
    @JsonView(JsonViews.Name.class)
    private boolean statisticsIsPublic;

    public boolean isMultiuserMode() {
        return multiuserMode;
    }

    public void setMultiuserMode(boolean multiuserMode) {
        this.multiuserMode = multiuserMode;
    }

    public boolean isPostPremoderation() {
        return postPremoderation;
    }

    public void setPostPremoderation(boolean postPremoderation) {
        this.postPremoderation = postPremoderation;
    }

    public boolean isStatisticsIsPublic() {
        return statisticsIsPublic;
    }

    public void setStatisticsIsPublic(boolean statisticsIsPublic) {
        this.statisticsIsPublic = statisticsIsPublic;
    }
}