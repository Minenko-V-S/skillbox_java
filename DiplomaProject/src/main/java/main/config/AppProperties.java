package main.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import main.enums.Settings;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.HashMap;
import java.util.Map;

@Validated
@Data
@NoArgsConstructor(force = true)
@Component
public class AppProperties {
    private final Map<String, Object> properties = new HashMap<>();
    private final GlobalSettings settings = new GlobalSettings();
    private final Map<String, Integer> sessions = new HashMap<>();

    @Data @NoArgsConstructor(force = true)
    public static class GlobalSettings {
        private boolean multiuserMode;
        private boolean postPremoderation;
        private boolean statisticsIsPublic;

        public Settings.Value isMultiuserMode() {
            return multiuserMode ? Settings.Value.YES : Settings.Value.NO;
        }

        public Settings.Value isPostPremoderation() {
            return postPremoderation ? Settings.Value.YES : Settings.Value.NO;
        }

        public Settings.Value isStatisticsIsPublic() {
            return statisticsIsPublic ? Settings.Value.YES : Settings.Value.NO;
        }

        public boolean getMultiuserMode() { return multiuserMode; }

        public boolean getPostPremoderation() { return postPremoderation; }

        public boolean getStatisticsIsPublic() { return statisticsIsPublic; }
    }

    //Stores user's session as a HashMap: sessions = { 'sessionId': userId, ... }
    public void addSession(String sessionId, int userId) {
        sessions.put(sessionId, userId);
    }

    //Returns user's ID by session ID
    public int getUserIdBySessionId(String sessionId) {
        return sessions.getOrDefault(sessionId, null);
    }

    //Removes user's session by it's ID from sessions store

    public int deleteSessionById(String sessionId) {
        return sessions.remove(sessionId);
    }

    public Map<String, Integer> getSessions() {
        return sessions;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    public GlobalSettings getSettings() {
        return settings;
    }
}
