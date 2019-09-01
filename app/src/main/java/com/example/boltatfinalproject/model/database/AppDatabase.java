package com.example.boltatfinalproject.model.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.boltatfinalproject.model.Answer;
import com.example.boltatfinalproject.model.Exam;
import com.example.boltatfinalproject.model.Question;

@Database(entities = {Question.class , Exam.class , Answer.class} , version = 6 , exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract AnswerDao answerDao();
    public abstract QuestionDao questionDao();
    public abstract ExamDao examDao();

    private static AppDatabase sDatabase;

    public static AppDatabase getDatabase(Context context){
        if (sDatabase==null){
            synchronized (AppDatabase.class){
                if (sDatabase==null){
                    sDatabase = Room.databaseBuilder(context.getApplicationContext() , AppDatabase.class , "ExamDatabase")
                            .fallbackToDestructiveMigration()
                            .build();

                }
            }
        }
        return sDatabase;
    }
}
