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
import com.fye.flipyourenglish.entities.Cards;
import com.fye.flipyourenglish.entities.Word;
import com.google.common.base.MoreObjects;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.ArrayList;
import java.util.List;

import static com.fye.flipyourenglish.db.CardTableCreator.COLUMN_ACTIVE;
import static com.fye.flipyourenglish.db.CardTableCreator.COLUMN_ID;
import static com.fye.flipyourenglish.db.CardTableCreator.COLUMN_WORD_A_ID;
import static com.fye.flipyourenglish.db.CardTableCreator.COLUMN_WORD_B_ID;
import static com.fye.flipyourenglish.db.CardTableCreator.TABLE_CARDS;
import static com.fye.flipyourenglish.utils.Utils.createArrayOfString;

/**
 * Created by Anton_Kutuzau on 4/18/2017.
 */

@EBean
public class CardRepository {

    @RootContext
    protected Context context;
    private SQLiteDatabase writeIntoDB;
    private SQLiteDatabase readFromDB;
    private DataBaseCreator dataBaseCreator;
    private WordRepository wordRepository;

    @AfterInject
    public void init() {
        dataBaseCreator = new DataBaseCreator(context);
        open();
        wordRepository = new WordRepository(context);
    }

    private void open() throws SQLException {
        writeIntoDB = dataBaseCreator.getWritableDatabase();
        readFromDB = dataBaseCreator.getReadableDatabase();
    }

    public void close() {
        dataBaseCreator.close();
    }

    public Card saveIfAbsent(String wordA, String wordB) {
        Card card = new Card();
        card.getWordA().setWord(wordA);
        card.getWordB().setWord(wordB);
        return saveIfAbsent(card);
    }

    public Card saveIfAbsent(Card card) {
        Word wordA = wordRepository.saveIfAbsent(card.getWordA(), WordsTablesCreator.TABLE_ENGLISH_WORDS);
        Word wordB = wordRepository.saveIfAbsent(card.getWordB(), WordsTablesCreator.TABLE_RUSSIAN_WORDS);
        Cards cards = find(createArrayOfString(wordA.getId(), wordB.getId()), createArrayOfString(COLUMN_WORD_A_ID, COLUMN_WORD_B_ID), "AND");
        return !cards.isEmpty() ? cards.getCurrent() : save(card, wordA, wordB);
    }

    private Card save(Card card, Word wordA, Word wordB) {
        ContentValues values = new ContentValues();
        values.put(CardTableCreator.COLUMN_WORD_A_ID, wordA.getId());
        values.put(CardTableCreator.COLUMN_WORD_B_ID, wordB.getId());
        values.put(CardTableCreator.COLUMN_ACTIVE, 1L);
        long cardId = writeIntoDB.insert(CardTableCreator.TABLE_CARDS, null, values);
        card.setId(cardId);
        return card;
    }

    public Card finddById(Long id) {

        Cards cards = find(createArrayOfString(String.valueOf(id)), createArrayOfString(COLUMN_ID), null);
        if(cards.isEmpty()) {
            return null;
        }
        return cards.getCurrent();
    }

    private Cards find(String[] values, String[] fields, String operator) {
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
        return new Cards(list);
    }

    public void saveAll(Cards cards) {
        cards.getAsList().forEach(this::saveIfAbsent);
    }

    public void update(Card card)
    {
        ContentValues values = new ContentValues();
        values.put(CardTableCreator.COLUMN_WORD_A_ID, card.getWordA().getId());
        values.put(CardTableCreator.COLUMN_WORD_B_ID, card.getWordB().getId());
        values.put(CardTableCreator.COLUMN_ACTIVE, card.getActive());
        readFromDB.update(TABLE_CARDS, values, COLUMN_ID + "= ?", new String[]{ String.valueOf(card.getId()) });
    }

    public void updateAll(Cards cards) {
        cards.getAsList().forEach(this::update);
    }

    public Cards findAll() {
        return find(null, null, null);
    }

    public Cards findCards(boolean isActive) {
        return find(createArrayOfString(isActive ? "1" : "0"), createArrayOfString(COLUMN_ACTIVE), null);
    }

    public void removeById(Long id) {
        readFromDB.delete(CardTableCreator.TABLE_CARDS, COLUMN_ID + "= ?", new String[] { String.valueOf(id) });
    }

    private Card createCard(Cursor cursor) {
        Card card = new Card();
        card.setId(Long.parseLong(cursor.getString(cursor.getColumnIndex(COLUMN_ID))));
        card.setWordA(wordRepository.getWordById(cursor.getLong(cursor.getColumnIndex(COLUMN_WORD_A_ID)), WordsTablesCreator.TABLE_ENGLISH_WORDS));
        card.setWordB(wordRepository.getWordById(cursor.getLong(cursor.getColumnIndex(COLUMN_WORD_B_ID)), WordsTablesCreator.TABLE_RUSSIAN_WORDS));
        card.setActive(1);
        return card;
    }
}
