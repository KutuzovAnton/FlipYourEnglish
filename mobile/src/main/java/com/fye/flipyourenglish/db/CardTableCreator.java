package com.fye.flipyourenglish.db;

import android.database.sqlite.SQLiteDatabase;

import org.androidannotations.annotations.EBean;


/**
 * Created by Anton_Kutuzau on 4/18/2017.
 */

@EBean
public class CardTableCreator {

    public static final String TABLE_CARDS = "cards";
    public static final String COLUMN_ID = "card_Id";
    public static final String COLUMN_WORD_A_ID = "word_a_Id";
    public static final String COLUMN_WORD_B_ID = "word_b_Id";
    public static final String COLUMN_ACTIVE = "active";

    public void onCreate(SQLiteDatabase db) {
        final String sqlQuery = "create table " + TABLE_CARDS + "( " +
                COLUMN_ID + " integer primary key autoincrement, " +
                COLUMN_WORD_A_ID + " integer not null, " +
                COLUMN_WORD_B_ID + " integer not null, " +
                COLUMN_ACTIVE + " integer not null," +
                "FOREIGN KEY(" + COLUMN_WORD_A_ID + ") REFERENCES " + WordsTablesCreator.TABLE_ENGLISH_WORDS +"("+ WordsTablesCreator.COLUMN_ID +")," +
                "FOREIGN KEY(" + COLUMN_WORD_B_ID + ") REFERENCES " + WordsTablesCreator.TABLE_RUSSIAN_WORDS +"("+ WordsTablesCreator.COLUMN_ID +"));";
        db.execSQL(sqlQuery);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
