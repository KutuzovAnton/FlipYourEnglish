package com.fye.flipyourenglish.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.fye.flipyourenglish.utils.Utils.DATABASE_NAME;

/**
 * Created by Anton_Kutuzau on 4/28/2017.
 */

public class BaseCreater extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private CardTableCreater cardTableCreater;
    private WordsTablesCreater wordsTablesCreater;

    public BaseCreater(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        cardTableCreater = new CardTableCreater();
        wordsTablesCreater= new WordsTablesCreater();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        cardTableCreater.onCreate(db);
        wordsTablesCreater.onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        cardTableCreater.onUpgrade(db, oldVersion, newVersion);
        wordsTablesCreater.onUpgrade(db, oldVersion, newVersion);
    }
}
