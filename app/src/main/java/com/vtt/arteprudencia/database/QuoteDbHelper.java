package com.vtt.arteprudencia.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by vtt on 07/05/17.
 */

public class QuoteDbHelper extends SQLiteOpenHelper {
    private static final String DEBUG_TAG = "QuoteDbHelper";

    private String mDbPATH; //Container folder where to move the db.
    private static String mDbName = "ElArteDeLaPrudencia.sqlite"; //Db name
    private SQLiteDatabase mDatabase;
    private final Context mContext;

    public QuoteDbHelper(Context context) {
        super(context, mDbName, null, 1);
        mContext = context;
        mDbPATH = "/data/data/" + mContext.getPackageName() + "/databases/";
    }

    public void createDataBase() throws IOException {
        if (!checkDataBase()) {//If the database is not on the database default folder.
            //This method creates an empty database into the default system folder
            //so it could be overwrite with the database store into assets folder
            this.getReadableDatabase();
            try {
                copyDataBase();
            } catch (IOException e) {
                Log.e(DEBUG_TAG, "Error copying database", e);
            }
        }
    }

    /**
     * Check if the database exist
     * @return true if it exists, false if it doesn't
     */

    private boolean checkDataBase() {
        SQLiteDatabase checkDb = null;
        try {
            String myPath = mDbPATH + mDbName;
            checkDb = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLException e) {
            Log.e(DEBUG_TAG, "Database does't exist", e);
        }
        if(checkDb != null) checkDb.close();

        return checkDb != null;
    }

    private void copyDataBase() throws IOException {
        //Open your local db as the input stream
        InputStream inputStream = mContext.getAssets().open(mDbName);
        //Path to the just created empty db
        String outFileName = mDbPATH + mDbName;
        //Open the empty db as the output stream
        OutputStream outputStream = new FileOutputStream(outFileName);

        byte[] buffer = new byte[1024];
        int length;
        while((length = inputStream.read(buffer)) > 0)
            outputStream.write(buffer, 0, length);

        outputStream.flush();
        outputStream.close();
        inputStream.close();
    }

    @Override
    public synchronized void close() {
        if(mDatabase != null) mDatabase.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
