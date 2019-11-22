package com.agungsubastian.themoviedbsql.Database;

import android.provider.BaseColumns;

public class DatabaseContract {

    static String TABLE_MOVIE = "MOVIE";
    static String TABLE_TV = "TV";
    public static class FavoriteColumns implements BaseColumns {
        public static String ID = "id";
        public static String TITLE = "title";
        public static String DESCRIPTION = "description";
        public static String DATE = "date";
        public static String SCORE = "score";
        public static String IMAGE = "image";
    }
}