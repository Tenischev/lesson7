package com.example.lesson7;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created with IntelliJ IDEA.
 * User: kris13
 * Date: 28.02.14
 * Time: 13:06
 * To change this template use File | Settings | File Templates.
 */
public class DbNews extends SQLiteOpenHelper {
    private static final int DB_VERSION = 2;
    private static final String DB_NAME = "rss";

    public static final String TABLE_NAME = "news";
    public static final String TITLE = "title";
    public static final String DESCRIP = "descrip";
    public static final String DATE = "date";
    public static final String LINK = "link";
    public static final String CHANEL = "chanel";
    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ( _id integer primary key autoincrement, "
            + TITLE + " TEXT, " + DESCRIP + " TEXT, " + DATE + " TEXT, " + LINK + " TEXT, " + CHANEL +  " TEXT)";

    public DbNews(Context context) {
        super(context, DB_NAME, null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL(dropDataBase());
            onCreate(db);
        }
    }

    private String dropDataBase() {
        return "DROP TABLE IF EXISTS " + DB_NAME;
    }
}
