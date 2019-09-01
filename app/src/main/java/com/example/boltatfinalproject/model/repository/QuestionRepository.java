package com.example.boltatfinalproject.model.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.boltatfinalproject.QuestionInterface;
import com.example.boltatfinalproject.model.Question;
import com.example.boltatfinalproject.model.database.AppDatabase;
import com.example.boltatfinalproject.model.database.QuestionDao;

import java.util.ArrayList;
import java.util.List;

public class QuestionRepository {
    private QuestionDao mDao;
    private LiveData<List<Question>> mAllData;
    private LiveData<List<Question>> mDataForExam;

    public QuestionRepository(Application application){
        AppDatabase database = AppDatabase.getDatabase(application);
        mDao = database.questionDao();
        mAllData = mDao.getAllQuestions();
    }

    public void insert(Question question, QuestionInterface questionInterface){
        new InsertQuestionAsyncTask(mDao,questionInterface).execute(question);
    }
    public LiveData<List<Question>> getAllData() {
        return mAllData;
    }

    public LiveData<List<Question>> getDataForExam(Long aLong) {
        mDataForExam = mDao.getQuestionsForExam(aLong);
        return mDataForExam;
    }

    class InsertQuestionAsyncTask extends AsyncTask<Question,Void,Void>{
        QuestionDao mDao;
        QuestionInterface mInterface;

         InsertQuestionAsyncTask(QuestionDao dao,QuestionInterface questionInterface) {
            mDao = dao;
            mInterface = questionInterface;
        }

        @Override
        protected Void doInBackground(Question... questions) {
            long questionId = mDao.insert(questions[0]);
            if (questionId>0){
                mInterface.QuestionSavedSuccessfully(questionId);
            }
            return null;
        }
    }


}
