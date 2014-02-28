package com.example.lesson7;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created with IntelliJ IDEA.
 * User: kris13
 * Date: 02.02.14
 * Time: 18:37
 * To change this template use File | Settings | File Templates.
 */
public class DbListRSS  extends SQLiteOpenHelper {
    private static final int DB_VERSION = 2;
    private static final String DB_NAME = "rss";

    public static final String TABLE_NAME = "listrss";
    public static final String NAME = "name";
    public static final String ADRES = "adres";
    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ( _id integer primary key autoincrement, "
            + ADRES + " TEXT, " + NAME + " TEXT)";

    public DbListRSS(Context context) {
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

    String dropDataBase() {
        return "DROP TABLE IF EXISTS " + DB_NAME;
    }
}

