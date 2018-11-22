package com.example.formation12.quizz.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.formation12.quizz.model.Question;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class QuestionDatabaseHelper extends SQLiteOpenHelper {

    private static QuestionDatabaseHelper instance;

    private static final String DATABASE_NAME = "questionsDatabase";

    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_QUESTIONS = "questions";

    private static final String KEY_QUESTIONS_ID = "id";
    private static final String KEY_QUESTIONS_INTITULE = "intitule";
    private static final String KEY_QUESTIONS_ANSWER1 = "answer_1";
    private static final String KEY_QUESTIONS_ANSWER2 = "answer_2";
    private static final String KEY_QUESTIONS_ANSWER3 = "answer_3";
    private static final String KEY_QUESTIONS_ANSWER4 = "answer_4";
    private static final String KEY_QUESTIONS_BONNEREPONSE = "bonnereponse";
    private static final String KEY_QUESTIONS_USERANSWER = "useranswer";


    /**
     * Constructor should be private to prevent direct instantiation.
     * Make a call to the static method "getInstance()" instead.
     */
    private QuestionDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized QuestionDatabaseHelper getInstance(Context context){
        if(instance==null){
            instance = new QuestionDatabaseHelper(context);
        }
        return instance;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_QUESTION_TABLE = "CREATE TABLE " + TABLE_QUESTIONS +
                "(" +
                    KEY_QUESTIONS_ID + " INTEGER PRIMARY KEY," +
                    KEY_QUESTIONS_INTITULE + " VARCHAR(200)," +
                    KEY_QUESTIONS_ANSWER1 + " VARCHAR(200)," +
                    KEY_QUESTIONS_ANSWER2 + " VARCHAR(200)," +
                    KEY_QUESTIONS_ANSWER3 + " VARCHAR(200)," +
                    KEY_QUESTIONS_ANSWER4 + " VARCHAR(200)," +
                    KEY_QUESTIONS_BONNEREPONSE + " VARCHAR(200)," +
                    KEY_QUESTIONS_USERANSWER + " INTEGER" +
                ")";
        db.execSQL(CREATE_QUESTION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion != newVersion){
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTIONS);
            onCreate(db);
        }
    }

    public List<Question> getAllQuestions(){

        List<Question> questions = new ArrayList<>();

        String QUESTIONS_SELECT = "SELECT * FROM " + TABLE_QUESTIONS;

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(QUESTIONS_SELECT, null);

        try{
            if(cursor.moveToFirst()){
                do{
                    Question newQuestion = new Question(cursor.getString(cursor.getColumnIndex(KEY_QUESTIONS_INTITULE)),4);
                    newQuestion.propositions = Arrays.asList(new String[]{
                            cursor.getString(cursor.getColumnIndex(KEY_QUESTIONS_ANSWER1)),
                            cursor.getString(cursor.getColumnIndex(KEY_QUESTIONS_ANSWER2)),
                            cursor.getString(cursor.getColumnIndex(KEY_QUESTIONS_ANSWER3)),
                            cursor.getString(cursor.getColumnIndex(KEY_QUESTIONS_ANSWER4))
                    });
                    newQuestion.bonneReponse = cursor.getString(cursor.getColumnIndex(KEY_QUESTIONS_BONNEREPONSE));
                    questions.add(newQuestion);
                } while (cursor.moveToNext());
            }
        }catch (Exception e) {
            Log.d("Debug DataBase", "Error while trying to get questions from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return  questions;
    }

    public void addQuestion(Question q){

        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {

            ContentValues values = new ContentValues();
            values.put(KEY_QUESTIONS_INTITULE, q.intitule);
            values.put(KEY_QUESTIONS_ANSWER1, q.propositions.get(0));
            values.put(KEY_QUESTIONS_ANSWER2, q.propositions.get(1));
            values.put(KEY_QUESTIONS_ANSWER3, q.propositions.get(2));
            values.put(KEY_QUESTIONS_ANSWER4, q.propositions.get(3));
            values.put(KEY_QUESTIONS_BONNEREPONSE, q.bonneReponse);

            if (checkAddOrUpdate(q)){
                db.insertOrThrow(TABLE_QUESTIONS,null, values);
            }
            else {
                db.update(TABLE_QUESTIONS, values, KEY_QUESTIONS_INTITULE+ " = ?", new String[]{q.intitule});
            }
            db.setTransactionSuccessful();

        } catch (Exception e){
            Log.d("Debug DataBase", "Error while trying to add question to database");
        }   finally {
            db.endTransaction();
        }
    }

    private boolean checkAddOrUpdate(Question q){

        List<Question> list = new ArrayList<>();

        String QUESTIONS_SELECT =
                        "SELECT *"+
                        " FROM " + TABLE_QUESTIONS +
                        " WHERE "+ TABLE_QUESTIONS+"."+KEY_QUESTIONS_INTITULE + " = " + "'"+q.intitule+"'";

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(QUESTIONS_SELECT, null);

        try{
            if(cursor.moveToFirst()){
                do{
                    Question newQuestion = new Question(cursor.getString(cursor.getColumnIndex(KEY_QUESTIONS_INTITULE)),4);
                    newQuestion.propositions = Arrays.asList(new String[]{
                            cursor.getString(cursor.getColumnIndex(KEY_QUESTIONS_ANSWER1)),
                            cursor.getString(cursor.getColumnIndex(KEY_QUESTIONS_ANSWER2)),
                            cursor.getString(cursor.getColumnIndex(KEY_QUESTIONS_ANSWER3)),
                            cursor.getString(cursor.getColumnIndex(KEY_QUESTIONS_ANSWER4))
                    });
                    newQuestion.bonneReponse = cursor.getString(cursor.getColumnIndex(KEY_QUESTIONS_BONNEREPONSE));
                    list.add(newQuestion);
                } while (cursor.moveToNext());
            }
        }catch (Exception e) {
            Log.d("Debug DataBase", "Error while trying to get questions from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return list.isEmpty();
    }


}
