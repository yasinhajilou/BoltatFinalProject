package com.example.boltatfinalproject.model.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.boltatfinalproject.model.Exam;

import java.util.List;

@Dao
public interface ExamDao {
    @Insert
    long insert(Exam exam);

    @Query("SELECT * FROM  exams")
    LiveData<List<Exam>> getAllExams();
}
