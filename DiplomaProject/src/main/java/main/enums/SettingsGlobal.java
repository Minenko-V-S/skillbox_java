package main.enums;

import main.config.Config;

public class SettingsGlobal {
    public enum Code {
        MULTIUSER_MODE(Config.STRING_MULTIUSER_MODE),
        POST_PREMODERATION(Config.STRING_POST_PREMODERATION),
        STATISTICS_IS_PUBLIC(Config.STRING_STATISTICS_IS_PUBLIC);

        private final String name;

        Code(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    public enum Value {
        YES(Config.STRING_YES, true),
        NO(Config.STRING_NO, false);

        private final String name;
        private final boolean value;

        Value(String name, boolean value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public boolean getValue() {
            return value;
        }
    }
}