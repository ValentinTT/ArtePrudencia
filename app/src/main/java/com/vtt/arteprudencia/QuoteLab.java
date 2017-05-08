package com.vtt.arteprudencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.vtt.arteprudencia.database.QuoteCursorWrapper;
import com.vtt.arteprudencia.database.QuoteDbHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.vtt.arteprudencia.database.QuoteDbSchema.QuoteTable.NAME;
import static com.vtt.arteprudencia.database.QuoteDbSchema.QuoteTable.Cols;

/**
 * Created by vtt on 07/05/17.
 */

public class QuoteLab {
    private static final String DEBUG_TAG = "QuoteLab";

    private static QuoteLab sQuoteLab;

    private SQLiteDatabase mDatabase;
    private Context mContext;

    public static QuoteLab get(Context context) {
        if (sQuoteLab == null) return new QuoteLab(context);
        return sQuoteLab;
    }

    private QuoteLab (Context context) {
        mContext = context.getApplicationContext();
        QuoteDbHelper quoteDbHelper = new QuoteDbHelper(mContext);

        try {
            quoteDbHelper.createDataBase();
        } catch (IOException e) {
            Log.e(DEBUG_TAG, "Error in QuoteLab(Context", e);
        }
        mDatabase = quoteDbHelper.getWritableDatabase();
    }

    public List<Quote> getQuotes() {
        List<Quote> quotes = new ArrayList<>(300);
        QuoteCursorWrapper cursorWrapper = getQuotes(null, null);

        try {
            cursorWrapper.moveToFirst();
            while(!cursorWrapper.isAfterLast()) {
                quotes.add(cursorWrapper.getQuote());
                cursorWrapper.moveToNext();
            }
        } finally {
            cursorWrapper.close();
        }
        return quotes;
    }

    public List<Quote> getFavQuotes() {
        List<Quote> favQuotes = new ArrayList<>();
        QuoteCursorWrapper cursorWrapper = getQuotes(
                Cols.FAV + " = ?",
                new String[] {"1"}
        );

        try {
            cursorWrapper.moveToFirst();
            while(!cursorWrapper.isAfterLast()) {
                favQuotes.add(cursorWrapper.getQuote());
                cursorWrapper.moveToNext();
            }
        } finally {
            cursorWrapper.close();
        }
        return favQuotes;
    }

    public Quote getQuote(int id) {
        QuoteCursorWrapper cursorWrapper = getQuotes(
                Cols.ID + " = ?",
                new String[] {"" + id}
        );

        try {
            if (cursorWrapper.getCount() == 0) return null;
            else {
                cursorWrapper.moveToFirst();
                return cursorWrapper.getQuote();
            }
        } finally {
            cursorWrapper.close();
        }
    }

    public void updateQuote (Quote quote) {
        String idString = String.valueOf(quote.getQuoteId());
        ContentValues values = getContentValues(quote);

        mDatabase.update(NAME, values, Cols.ID + " = ?", new String[] {idString});
    }

    private static ContentValues getContentValues(Quote quote) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Cols.ID, quote.getQuoteId());
        contentValues.put(Cols.TITLE, quote.getQuoteTitle());
        contentValues.put(Cols.BODY, quote.getQuoteBody());
        contentValues.put(Cols.BODY, quote.isFav()? 1 : 0);

        return contentValues;
    }

    private QuoteCursorWrapper getQuotes(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null,
                null);
        return new QuoteCursorWrapper(cursor);
    }
}





















