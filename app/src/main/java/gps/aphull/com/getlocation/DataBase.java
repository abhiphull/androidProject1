package gps.aphull.com.getlocation;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by abishake on 12/24/2015.
 */
public class  DataBase extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DICTIONARY_TABLE_NAME = "coord";
    private static final String DATABASE_NAME = "gps";
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + DICTIONARY_TABLE_NAME;
    /*private static final String DICTIONARY_TABLE_CREATE =
            "CREATE TABLE " + DICTIONARY_TABLE_NAME + " (" +
                    KEY_WORD + " TEXT, " +
                    KEY_DEFINITION + " TEXT);";*/

    DataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("database", "creating");
        db.execSQL(SQL_DELETE_ENTRIES);
        db.execSQL("CREATE TABLE " + DICTIONARY_TABLE_NAME + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, long TEXT, lat Text);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
