package com.example.boltatfinalproject.model.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.boltatfinalproject.model.Question;

import java.util.List;

@Dao
public interface QuestionDao {
    @Insert
    long insert(Question question);

    @Query("SELECT * FROM questions")
    LiveData<List<Question>> getAllQuestions();

    @Query("SELECT * FROM questions WHERE ExamId=:examId")
    LiveData<List<Question>> getQuestionsForExam(long examId);
}
