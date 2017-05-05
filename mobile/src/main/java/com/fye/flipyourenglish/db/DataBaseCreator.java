package com.fye.flipyourenglish.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.fye.flipyourenglish.utils.Utils.DATABASE_NAME;

/**
 * Created by Anton_Kutuzau on 4/28/2017.
 */

public class DataBaseCreator extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private CardTableCreator cardTableCreator;
    private WordsTablesCreator wordsTablesCreator;

    public DataBaseCreator(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        cardTableCreator = new CardTableCreator();
        wordsTablesCreator = new WordsTablesCreator();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        cardTableCreator.onCreate(db);
        wordsTablesCreator.onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        cardTableCreator.onUpgrade(db, oldVersion, newVersion);
        wordsTablesCreator.onUpgrade(db, oldVersion, newVersion);
    }
}
