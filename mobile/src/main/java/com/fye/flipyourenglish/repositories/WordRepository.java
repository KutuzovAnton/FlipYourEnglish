package com.fye.flipyourenglish.repositories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.fye.flipyourenglish.db.DataBaseCreator;
import com.fye.flipyourenglish.db.WordsTablesCreator;
import com.fye.flipyourenglish.entities.Word;


import static com.fye.flipyourenglish.db.WordsTablesCreator.COLUMN_ID;
import static com.fye.flipyourenglish.db.WordsTablesCreator.COLUMN_WORD;

/**
 * Created by Anton_Kutuzau on 4/18/2017.
 */

public class WordRepository {

    private SQLiteDatabase readFromDB;
    private SQLiteDatabase writeIntoDB;
    private DataBaseCreator dataBaseCreator;

    public WordRepository(Context context) {
        dataBaseCreator = new DataBaseCreator(context);
        open();
    }

    public void open() throws SQLException {
        writeIntoDB = dataBaseCreator.getWritableDatabase();
        readFromDB = dataBaseCreator.getReadableDatabase();
    }

    public void close() {
        dataBaseCreator.close();
    }

    public Word save(Word word, String tableName) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_WORD, word.getWord());
        long wordId = writeIntoDB.insert(tableName, null, values);
        word.setId(wordId);
        return word;
    }

    public Word saveIfAbsent(Word word, String tableName) {
        Word wordFromDB = getWordByValue(word.getWord(), tableName);
        if(wordFromDB == null) {
            return save(word, tableName);
        }
        return wordFromDB;
    }

    public Word getWordById(Long id, String table) {
        return getWord(table, COLUMN_ID, String.valueOf(id));
    }

    public Word getWordByValue(String word, String table) {
        return getWord(table, COLUMN_WORD, word);
    }

    private Word getWord(String table, String column, String value) {

        Cursor cursor = readFromDB.query(table,
                new String[]{COLUMN_ID, COLUMN_WORD},
                " " + column + " = ?",
                new String[] { value },
                null, null, null, null);

        cursor.moveToFirst();
        if (!cursor.isFirst()) {
            return null;
        }


        return new Word(cursor.getLong(cursor.getColumnIndex(COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(COLUMN_WORD)));
    }
    public void removeById(Long englishWordId, Long russianWordId){
        readFromDB.delete(WordsTablesCreator.TABLE_ENGLISH_WORDS, COLUMN_ID + "= ?", new String[] { String.valueOf(englishWordId) });
        readFromDB.delete(WordsTablesCreator.TABLE_RUSSIAN_WORDS, COLUMN_ID + "= ?", new String[] { String.valueOf(russianWordId) });
    }
}
