package com.fye.flipyourenglish.db;

import android.database.sqlite.SQLiteDatabase;

import org.androidannotations.annotations.EBean;

/**
 * Created by Anton_Kutuzau on 4/18/2017.
 */

@EBean
public class WordsTablesCreator {

    public static final String TABLE_ENGLISH_WORDS = "english_words";
    public static final String TABLE_RUSSIAN_WORDS = "russian_words";
    public static final String COLUMN_ID = "word_Id";
    public static final String COLUMN_WORD = "word";

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(getSql(TABLE_ENGLISH_WORDS));
        db.execSQL(getSql(TABLE_RUSSIAN_WORDS));
    }

    private String getSql(String tableName) {
        return  "create table " + tableName + "( " +
                COLUMN_ID + " integer primary key autoincrement, " +
                COLUMN_WORD + " text not null UNIQUE);";
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
