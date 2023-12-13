package com.example.sequenceapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;


public class DatabaseHelper extends SQLiteOpenHelper {
    private  static  int DATABSE_VERSION = 1;
    private static final String DATABASE_NAME = "GameScores";
    private static final String TABLE_SCORES="Scores";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_SCORES = "score";

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABSE_VERSION);

    }
    @Override
    public void onCreate(SQLiteDatabase db){
        String CREATE_SCORES_TABLE = "CREATE TABLE " + TABLE_SCORES + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_SCORES + " TEXT" + ")";

        db.execSQL(CREATE_SCORES_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCORES);

        // Create tables again
        onCreate(db);
    }
    public void emptyScores() {
        // Drop older table if existed
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCORES);

        // Create tables again
        onCreate(db);
    }
    void addScore(Score score) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, score.getName()); // Contact Name
        values.put(KEY_SCORES, score.getFinalScore()); // Contact Phone

        // Inserting Row
        db.insert(TABLE_SCORES, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }
    public List<Score> getAllScores() {
        List<Score> scoreList = new ArrayList<Score>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_SCORES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Score score = new Score();
                score.setID(Integer.parseInt(cursor.getString(0)));
                score.setName(cursor.getString(1));
                score.setFinalScore(cursor.getString(2));
                // Adding contact to list
                scoreList.add(score);
            } while (cursor.moveToNext());
        }
        return scoreList;
    }
    public int getScoreCount() {
        int count = 0;
        String countQuery = "SELECT  * FROM " + TABLE_SCORES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        count = cursor.getCount();
        cursor.close();

        // return count
        return count;
    }
    //gets the top 5 scores.
    public List<Score> getTopScores() {
        List<Score> topScoresList = new ArrayList<>();

        // Select top scores query
        String selectTopScoresQuery = "SELECT * FROM " + TABLE_SCORES +
                " ORDER BY CAST (" + KEY_SCORES + " AS INTEGER) DESC LIMIT " + 5;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectTopScoresQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Score score = new Score();
                score.setID(Integer.parseInt(cursor.getString(0)));
                score.setName(cursor.getString(1));
                score.setFinalScore(cursor.getString(2));
                // Adding score to list
                topScoresList.add(score);
            } while (cursor.moveToNext());
        }

        // Close the cursor and database
        cursor.close();
        db.close();

        return topScoresList;
    }





}
