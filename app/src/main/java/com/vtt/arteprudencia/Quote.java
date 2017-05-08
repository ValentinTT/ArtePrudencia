package com.vtt.arteprudencia;

/**
 * Created by vtt on 07/05/17.
 */
public class Quote {
    private int mQuoteId;
    private String mQuoteTitle;
    private String mQuoteBody;
    private boolean isFav;

    public Quote(int quoteId, String quoteTitle, String quoteBody, boolean isFav) {
        mQuoteId = quoteId;
        mQuoteTitle = quoteTitle;
        mQuoteBody = quoteBody;
        this.isFav = isFav;
    }

    public int getQuoteId() {
        return mQuoteId;
    }
    public String getQuoteTitle() {
        return mQuoteTitle;
    }

    public String getQuoteBody() {
        return mQuoteBody;
    }

    public boolean isFav() {
        return isFav;
    }

    public void setFav(boolean fav) {
        isFav = fav;
    }
}
