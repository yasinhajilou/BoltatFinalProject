package com.example.boltatfinalproject.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.boltatfinalproject.AnswerInterface;
import com.example.boltatfinalproject.model.Answer;
import com.example.boltatfinalproject.model.repository.AnswerRepository;

import java.util.List;

public class AnswerViewModel extends AndroidViewModel {
    private AnswerRepository mRepository;
    private LiveData<List<Answer>> mAllData;

    public AnswerViewModel(@NonNull Application application) {
        super(application);
        mRepository = new AnswerRepository(application);
        mAllData = mRepository.getAllData();
    }

    public void insert(Answer[] answer) {
        mRepository.insert(answer);
    }

    public LiveData<List<Answer>> getAllData() {
        return mAllData;
    }

    public void getDataForQuestion(long questionId , AnswerInterface answerInterface) {
        mRepository.getDataForQuestion(questionId , answerInterface);
    }
}
