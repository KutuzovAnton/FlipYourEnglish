package com.fye.flipyourenglish.db;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Anton_Kutuzau on 4/18/2017.
 */

public class WordsTablesCreater extends SQLiteOpenHelper {

    public static final String TABLE_ENGLISH_WORDS = "english_words";
    public static final String TABLE_RUSSIAN_WORDS = "russian_words";
    public static final String COLUMN_ID = "word_Id";
    public static final String COLUMN_WORD_A_ID = "word";

    private static final String DATABASE_NAME = "core.db";
    private static final int DATABASE_VERSION = 1;


    public WordsTablesCreater(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(getSql(TABLE_ENGLISH_WORDS));
        db.execSQL(getSql(TABLE_RUSSIAN_WORDS));
    }

    private String getSql(String tableName) {
        return  "create table " + tableName + "( " +
                COLUMN_ID + " integer primary key autoincrement, " +
                COLUMN_WORD_A_ID + " integer not null);";
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
