package com.agungsubastian.themoviedbsql.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper {
    private static int DATABASE_VERSION = 1;
    private static String DATABASE_NAME = "themoviedbsql";

    DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE_MOVIE = "create table " + DatabaseContract.TABLE_MOVIE + " (" +
                DatabaseContract.FavoriteColumns.ID + " integer primary key autoincrement , " +
                DatabaseContract.FavoriteColumns.TITLE + " text not null, " +
                DatabaseContract.FavoriteColumns.DATE + " text not null, " +
                DatabaseContract.FavoriteColumns.DESCRIPTION + " text not null, " +
                DatabaseContract.FavoriteColumns.IMAGE + " text not null, " +
                DatabaseContract.FavoriteColumns.SCORE + " text not null ON CONFLICT REPLACE" +
                ");";

        String CREATE_TABLE_TV = "create table " + DatabaseContract.TABLE_TV + " (" +
                DatabaseContract.FavoriteColumns.ID + " integer primary key autoincrement , " +
                DatabaseContract.FavoriteColumns.TITLE + " text not null, " +
                DatabaseContract.FavoriteColumns.DATE + " text not null, " +
                DatabaseContract.FavoriteColumns.DESCRIPTION + " text not null, " +
                DatabaseContract.FavoriteColumns.IMAGE + " text not null, " +
                DatabaseContract.FavoriteColumns.SCORE + " text not null ON CONFLICT REPLACE" +
                ");";

        sqLiteDatabase.execSQL(CREATE_TABLE_MOVIE);
        sqLiteDatabase.execSQL(CREATE_TABLE_TV);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TABLE_MOVIE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TABLE_TV);
        onCreate(sqLiteDatabase);
    }
}
