package com.example.boltatfinalproject.model.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.boltatfinalproject.model.Answer;
import com.example.boltatfinalproject.model.Exam;

import java.util.List;

@Dao
public interface AnswerDao {
    @Insert
    long insert(Answer answer);

    @Query("SELECT * FROM answers")
    LiveData<List<Answer>> getAllAnswers();

    @Query("SELECT * FROM answers WHERE QuestionId=:questionId")
    List<Answer> getAllAnswersForQuestion(long questionId);
}
