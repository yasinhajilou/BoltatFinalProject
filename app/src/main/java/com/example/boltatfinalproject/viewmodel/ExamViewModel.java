package com.example.boltatfinalproject.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.boltatfinalproject.ExamInterface;
import com.example.boltatfinalproject.model.Exam;
import com.example.boltatfinalproject.model.repository.ExamRepository;

import java.util.List;

public class ExamViewModel extends AndroidViewModel implements ExamInterface{
    private ExamRepository mRepository;
    private LiveData<List<Exam>> mAllData;
    private MutableLiveData<Long> mExamMutableLiveData;

    public ExamViewModel(@NonNull Application application) {
        super(application);
        mRepository = new ExamRepository(application);
        mAllData = mRepository.getAllData();
        mExamMutableLiveData = new MutableLiveData<>();
    }

    public void insert(Exam exam) {
        mRepository.insert(exam,this);
    }

    public LiveData<List<Exam>> getAllData() {
        return mAllData;
    }

    public MutableLiveData<Long> getExamMutableLiveData() {
        return mExamMutableLiveData;
    }

    @Override
    public void examSavedSuccessfully(long examId) {
        mExamMutableLiveData.postValue(examId);
    }
}
