package main.config;


    public class Config {
        public static final String STRING_MULTIUSER_MODE = "Многопользовательский режим";
        public static final String STRING_POST_PREMODERATION = "Премодерация постов";
        public static final String STRING_STATISTICS_IS_PUBLIC = "Показывать всем статистику блога";

        public static final String STRING_YES = "Да";
        public static final String STRING_NO = "Нет";

        public static final String STRING_POST_NOT_FOUND = "Пост с идентификатором '%d' не найден!";
        public static final String STRING_POST_INVALID_TAG = "Тег '%s' не найден!";
        public static final String STRING_POST_INVALID_DATE = "Неправильный формат даты! Используйте: 'yyyy-MM-dd'.";
        public static final int INT_POST_MIN_QUERY_LENGTH = 3;
        public static final String STRING_POST_INVALID_QUERY = String.format("Параметр 'query' должен быть " +
                "не менее %d символов.", INT_POST_MIN_QUERY_LENGTH);

        public static final String STRING_POST_NO_SUCH_MODE = "Неподдерживаемый режим вывода: '%s'!";
        public static final String STRING_MODERATED_POST_DATE_FORMAT = "dd-MM-yyyy HH:mm";

        public static final String STRING_NEW_POST_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm";



}
