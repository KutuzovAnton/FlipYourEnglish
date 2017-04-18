package com.fye.flipyourenglish.db;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Anton_Kutuzau on 4/18/2017.
 */

public class CardTableCreater extends SQLiteOpenHelper {

    public static final String TABLE_CARDS = "cards";
    public static final String COLUMN_ID = "card_Id";
    public static final String COLUMN_WORD_A_ID = "word_a_Id";
    public static final String COLUMN_WORD_B_ID = "word_b_Id";
    public static final String COLUMN_ACTIVE = "active";

    private static final String DATABASE_NAME = "core.db";
    private static final int DATABASE_VERSION = 1;


    public CardTableCreater(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        final String sqlQuery = "create table " + TABLE_CARDS + "( " +
                COLUMN_ID + " integer primary key autoincrement, " +
                COLUMN_WORD_A_ID + " integer not null, " +
                COLUMN_WORD_B_ID + " integer not null, " +
                COLUMN_ACTIVE + " integer not null);";
        db.execSQL(sqlQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
