package com.vtt.arteprudencia.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.vtt.arteprudencia.Quote;
import com.vtt.arteprudencia.database.QuoteDbSchema.QuoteTable.Cols;

/**
 * Created by vtt on 07/05/17.
 */

public class QuoteCursorWrapper extends CursorWrapper {
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public QuoteCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Quote getQuote() {
        int idInt = getInt(getColumnIndex(Cols.ID));
        String titleString = getString(getColumnIndex(Cols.TITLE));
        String bodyString = getString(getColumnIndex(Cols.BODY));
        int isFav = getInt(getColumnIndex(Cols.FAV));

        Quote quote = new Quote(idInt, titleString, bodyString, isFav != 0);
        return quote;
    }
}
