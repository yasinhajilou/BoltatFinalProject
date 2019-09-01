package com.example.boltatfinalproject.model;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "answers", foreignKeys = @ForeignKey(entity = Question.class,
        parentColumns = "QId", childColumns = "QuestionId"))
public class Answer {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_ID")
    private long id;

    @ColumnInfo(name = "AnswerTitle")
    private String title;

    @ColumnInfo(name = "State")
    private boolean state;

    @ColumnInfo(name = "QuestionId")
    private long questionId;

    public Answer(long id, String title, boolean state, long questionId) {
        this.id = id;
        this.title = title;
        this.state = state;
        this.questionId = questionId;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public boolean getState() {
        return state;
    }

    public long getQuestionId() {
        return questionId;
    }
}
