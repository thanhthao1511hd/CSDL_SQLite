package com.example.csdl_sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SQLHelper extends SQLiteOpenHelper {

    private static final String TAG = "MyDatabaseHelper";
    private static final String DATABASE_NAME = "DICTIONARY_DB";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_WORD = "WORD";
    private static final String ID_COLUMN = "id";
    private static final String WORD_COLUMN = "word";
    private static final String MEAN_COLUMN = "mean";
    private static final String CREATE_WORD_TABLE_SQL =
            "CREATE TABLE IF NOT EXISTS " + TABLE_WORD + " (" +
                    ID_COLUMN + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                    WORD_COLUMN + " TEXT NOT NULL," +
                    MEAN_COLUMN + " TEXT NOT NULL" +
                    ")";
//    private static SQLHelper sInstance;
//    public static SQLHelper getInstance(Context context) {
//        if (sInstance == null) {
//            sInstance = new SQLHelper(context.getApplicationContext());
//        }
//        return sInstance;
//    }
    public SQLHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.e(TAG, "onCreate: ");
        try{
// db.execSQL("DROP TABLE IF EXISTS " + TABLE_WORD);
            db.execSQL(CREATE_WORD_TABLE_SQL);}
        catch (Exception e){
            Log.e(TAG,e.toString());
        }
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.e(TAG, "onUpgrade: ");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WORD);
        onCreate(db);
    }

    public boolean insertWord(Word word) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(WORD_COLUMN, word.getWord());
        values.put(MEAN_COLUMN, word.getMean());
        long rowId = db.insert(TABLE_WORD, null, values);
        db.close();
//        if (rowId != -1)
//            return true;
        return false;
    }
    public Word getWord(String id) {
        SQLiteDatabase db = getReadableDatabase();
        Word word = null;
        Cursor cursor = db.query(TABLE_WORD, new String[]{ID_COLUMN,
                        WORD_COLUMN, MEAN_COLUMN},
                WORD_COLUMN + " = ?",
                new String[]{String.valueOf(id)}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            word = new Word(cursor.getInt(0), cursor.getString(1),
                    cursor.getString(2));
            cursor.close();
        }
        db.close();
        return word;
    }
    public List<Word> getAllWord() {
        SQLiteDatabase db = getReadableDatabase();
        List<Word> words = new ArrayList<Word>();
        String sql = "SELECT * FROM " + TABLE_WORD;
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                words.add(new
                        Word(cursor.getInt(cursor.getColumnIndex(ID_COLUMN)),
                        cursor.getString(cursor.getColumnIndex(WORD_COLUMN)),
                        cursor.getString(cursor.getColumnIndex(MEAN_COLUMN))));
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return words;
    }
    //Đếm tổng số dòng trong database
    public int getTotalWord() {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_WORD;
        Cursor cursor = db.rawQuery(sql, null);
        int totalRows = cursor.getCount();
        cursor.close();
        return totalRows;
    }
    public int updateWord(Word word) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(WORD_COLUMN, word.getWord());
        values.put(MEAN_COLUMN, word.getMean());
        int rowEffect = db.update(TABLE_WORD, values, ID_COLUMN + " = ?",
                new String[]{String.valueOf(word.getId())});
        db.close();
        return rowEffect;
    }

    public int deleteAllWord() {
        SQLiteDatabase db = getReadableDatabase();
        int rowEffect = db.delete(TABLE_WORD, null,null);
        db.close();
        return rowEffect;
    }
    public int deleteWord(Word word) {
        SQLiteDatabase db = getReadableDatabase();
        int rowEffect = db.delete(TABLE_WORD, ID_COLUMN + " = ?", new
                String[]{String.valueOf(word.getId())});
        db.close();
        return rowEffect;
    }

}
