package com.example.boltatfinalproject.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "questions", foreignKeys = @ForeignKey(entity = Exam.class,
        parentColumns = "id", childColumns = "ExamId"))
public class Question {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "QId")
    private long id;

    @ColumnInfo(name = "QuestionTitle")
    private String title;

    @ColumnInfo(name = "ExamId")
    private long examId;

    public Question(long id, String title, long examId) {
        this.id = id;
        this.title = title;
        this.examId = examId;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public long getExamId() {
        return examId;
    }
}
