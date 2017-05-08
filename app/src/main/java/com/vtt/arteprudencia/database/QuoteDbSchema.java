package com.vtt.arteprudencia.database;

/**
 * Created by vtt on 07/05/17.
 */

public class QuoteDbSchema {
    public static final class QuoteTable {
        public static final String NAME = "frases";

        public static final class Cols {
            public static final String ID = "id";
            public static final String TITLE = "F_TITULO";
            public static final String BODY = "F_CUERPO";
            public static final String FAV = "F_FAVORITA";
        }
    }
}
