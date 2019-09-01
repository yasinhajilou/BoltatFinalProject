package com.example.boltatfinalproject.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.boltatfinalproject.QuestionInterface;
import com.example.boltatfinalproject.model.Question;
import com.example.boltatfinalproject.model.repository.QuestionRepository;

import java.util.List;

public class QuestionViewModel extends AndroidViewModel implements QuestionInterface {
    private QuestionRepository mRepository;
    private LiveData<List<Question>> mAllData;
    private LiveData<List<Question>> mDataForExam;
    private MutableLiveData<Long> mQuestionIdMutableLiveData;

    public QuestionViewModel(@NonNull Application application) {
        super(application);
        mRepository = new QuestionRepository(application);
        mAllData = mRepository.getAllData();
        mQuestionIdMutableLiveData = new MutableLiveData<>();
    }

    public void insert(Question question) {
        mRepository.insert(question,this);
    }

    public LiveData<List<Question>> getAllData() {
        return mAllData;
    }

    public LiveData<List<Question>> getDataForExam(long examId) {
        mDataForExam = mRepository.getDataForExam(examId);
        return mDataForExam;
    }

    public MutableLiveData<Long> getQuestionIdMutableLiveData() {
        return mQuestionIdMutableLiveData;
    }

    @Override
    public void QuestionSavedSuccessfully(long questionId) {
        mQuestionIdMutableLiveData.postValue(questionId);
    }
}
