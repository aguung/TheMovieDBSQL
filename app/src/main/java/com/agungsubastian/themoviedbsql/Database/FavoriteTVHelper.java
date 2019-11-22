package com.agungsubastian.themoviedbsql.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.agungsubastian.themoviedbsql.Database.DatabaseContract.FavoriteColumns.ID;
import static com.agungsubastian.themoviedbsql.Database.DatabaseContract.TABLE_TV;

public class FavoriteTVHelper {
    private static final String DATABASE_TABLE = TABLE_TV;
    private static DatabaseHelper dataBaseHelper;
    private static FavoriteTVHelper INSTANCE;

    private static SQLiteDatabase database;

    public FavoriteTVHelper(Context context) {
        dataBaseHelper = new DatabaseHelper(context);
    }

    public static FavoriteTVHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new FavoriteTVHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        database = dataBaseHelper.getWritableDatabase();
    }

    public void close() {
        dataBaseHelper.close();

        if (database.isOpen())
            database.close();
    }

    public Cursor queryAll() {
        return database.query(
                TABLE_TV,
                null,
                null,
                null,
                null,
                null,
                ID + " ASC"
        );
    }

    public Cursor queryById(String id) {
        return database.query(DATABASE_TABLE, null
                , ID + " = ?"
                , new String[]{id}
                , null
                , null
                , null
                , null);
    }

    public void insert(ContentValues values) {
        database.insert(DATABASE_TABLE, null, values);
    }
    public void deleteById(String id) {
        database.delete(DATABASE_TABLE, ID + " = ?", new String[]{id});
    }
}
