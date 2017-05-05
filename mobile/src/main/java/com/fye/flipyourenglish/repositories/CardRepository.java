package com.fye.flipyourenglish.repositories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.fye.flipyourenglish.db.DataBaseCreator;
import com.fye.flipyourenglish.db.CardTableCreator;
import com.fye.flipyourenglish.db.WordsTablesCreator;
import com.fye.flipyourenglish.entities.Card;
import com.fye.flipyourenglish.entities.Word;
import com.google.common.base.MoreObjects;

import java.util.ArrayList;
import java.util.List;

import static com.fye.flipyourenglish.db.CardTableCreator.COLUMN_ACTIVE;
import static com.fye.flipyourenglish.db.CardTableCreator.COLUMN_ID;
import static com.fye.flipyourenglish.db.CardTableCreator.COLUMN_WORD_A_ID;
import static com.fye.flipyourenglish.db.CardTableCreator.COLUMN_WORD_B_ID;
import static com.fye.flipyourenglish.db.CardTableCreator.TABLE_CARDS;

/**
 * Created by Anton_Kutuzau on 4/18/2017.
 */

public class CardRepository {

    private SQLiteDatabase writeIntoDB;
    private SQLiteDatabase readFromDB;
    private DataBaseCreator dataBaseCreator;
    private WordRepository wordRepository;

    public CardRepository(Context context) {
        dataBaseCreator = new DataBaseCreator(context);
        open();
        wordRepository = new WordRepository(context);
    }

    public void open() throws SQLException {
        writeIntoDB = dataBaseCreator.getWritableDatabase();
        readFromDB = dataBaseCreator.getReadableDatabase();
    }

    public void close() {
        dataBaseCreator.close();
    }

    public Card save(Card card) {
        Word wordA = wordRepository.saveIfAbsent(card.getWordA(), WordsTablesCreator.TABLE_ENGLISH_WORDS);
        Word wordB = wordRepository.saveIfAbsent(card.getWordB(), WordsTablesCreator.TABLE_RUSSIAN_WORDS);
        List<Card> cards = find(new String[]{String.valueOf(wordA.getId()), String.valueOf(wordB.getId())},
                new String[]{ COLUMN_WORD_A_ID, COLUMN_WORD_B_ID }, "AND");
        if(!cards.isEmpty()) {
            return cards.get(0);
        }
        ContentValues values = new ContentValues();
        values.put(CardTableCreator.COLUMN_WORD_A_ID, wordA.getId());
        values.put(CardTableCreator.COLUMN_WORD_B_ID, wordB.getId());
        values.put(CardTableCreator.COLUMN_ACTIVE, 1L);
        long cardId = writeIntoDB.insert(CardTableCreator.TABLE_CARDS, null, values);
        card.setId(cardId);
        return card;
    }

    public Card finddById(Long id) {

        List<Card> cards = find(new String[] { String.valueOf(id) }, new String[] { COLUMN_ID }, null);
        if(cards.isEmpty()) {
            return null;
        }
        return cards.get(0);
    }

    private List<Card> find(String[] values, String[] fields, String operator) {
        List<Card> list = new ArrayList<>();
        String allFields = null;

        if(fields != null) {
            allFields = "";
            for (String field : fields) {
                if (!allFields.isEmpty() && operator != null)
                    allFields += " " + operator + " ";
                allFields += field + "= ?";
            }
        }

        Cursor cursor = readFromDB.query(CardTableCreator.TABLE_CARDS,
                new String[]{COLUMN_ID, COLUMN_WORD_A_ID, COLUMN_WORD_B_ID, COLUMN_ACTIVE},
                allFields,
                values,
                null, null, null, null);

        cursor.moveToFirst();
        if (cursor.isFirst()) {

            do {
                list.add(createCard(cursor));
            } while (cursor.moveToNext());
        }
        return list;
    }

    public void saveAll(List<Card> cards) {
        cards.forEach(this::save);
    }

    public void update(Card card)
    {
        ContentValues values = new ContentValues();
        values.put(CardTableCreator.COLUMN_WORD_A_ID, card.getWordA().getId());
        values.put(CardTableCreator.COLUMN_WORD_B_ID, card.getWordB().getId());
        values.put(CardTableCreator.COLUMN_ACTIVE, card.getActive());
        readFromDB.update(TABLE_CARDS, values, COLUMN_ID + "= ?", new String[]{ String.valueOf(card.getId()) });
    }

    public void updateAll(List<Card> cards) {
        cards.forEach(this::update);
    }

    private Card createCard(Cursor cursor) {
        Card card = new Card();
        card.setId(Long.parseLong(cursor.getString(cursor.getColumnIndex(COLUMN_ID))));
        card.setWordA(wordRepository.getWordById(cursor.getLong(cursor.getColumnIndex(COLUMN_WORD_A_ID)), WordsTablesCreator.TABLE_ENGLISH_WORDS));
        card.setWordB(wordRepository.getWordById(cursor.getLong(cursor.getColumnIndex(COLUMN_WORD_B_ID)), WordsTablesCreator.TABLE_RUSSIAN_WORDS));
        card.setActive(1);
        return card;
    }

    public List<Card> findAll() {
        return MoreObjects.firstNonNull(find(null, null, null), new ArrayList<>());
    }

    public List<Card> findActiveCards() {
        return MoreObjects.firstNonNull(find(new String[] { "1" }, new String[] { COLUMN_ACTIVE }, null), new ArrayList<>());
    }

    public List<Card> findNotActiveCards() {
        return MoreObjects.firstNonNull(find(new String[] { "0" }, new String[] { COLUMN_ACTIVE }, null), new ArrayList<>());
    }


    public void removeById(Long id) {
        readFromDB.delete(CardTableCreator.TABLE_CARDS, COLUMN_ID + "= ?", new String[] { String.valueOf(id) });
    }
}
