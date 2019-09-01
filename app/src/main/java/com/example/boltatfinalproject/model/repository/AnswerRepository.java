package com.example.boltatfinalproject.model.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.boltatfinalproject.AnswerInterface;
import com.example.boltatfinalproject.model.Answer;
import com.example.boltatfinalproject.model.database.AnswerDao;
import com.example.boltatfinalproject.model.database.AppDatabase;
import com.example.boltatfinalproject.model.database.QuestionDao;

import java.util.List;

public class AnswerRepository {
    private AnswerDao mDao;
    private LiveData<List<Answer>> mAllData;
    private List<Answer> mDataForQuestion;

    public AnswerRepository(Application application) {
        AppDatabase database = AppDatabase.getDatabase(application);
        mDao = database.answerDao();
        mAllData = mDao.getAllAnswers();
    }

    public void insert(Answer[] answer){
        new InsertAnswerAsyncTask(mDao).execute(answer);
    }
    public LiveData<List<Answer>> getAllData() {
        return mAllData;
    }

    public void getDataForQuestion(long questionId , AnswerInterface answerInterface) {
        new QueryFindAnswerForQuestion(mDao,answerInterface).execute(questionId);
    }

    class InsertAnswerAsyncTask extends AsyncTask<Answer,Void,Void>{
        private AnswerDao mDao;

        public InsertAnswerAsyncTask(AnswerDao dao) {
            mDao = dao;
        }

        @Override
        protected Void doInBackground(Answer... answers) {
            for (Answer answer : answers){
                mDao.insert(answer);

            }
            return null;
        }
    }

    class QueryFindAnswerForQuestion extends AsyncTask<Long,Void,List<Answer>>{
        private AnswerDao mDao;
        AnswerInterface mAnswerInterface;

        public QueryFindAnswerForQuestion(AnswerDao dao,AnswerInterface answerInterface) {
            mDao = dao;
            mAnswerInterface = answerInterface;
        }

        @Override
        protected List<Answer> doInBackground(Long... longs) {
            return mDao.getAllAnswersForQuestion(longs[0]);
        }

        @Override
        protected void onPostExecute(List<Answer> answers) {
            super.onPostExecute(answers);
            mAnswerInterface.AnswersForQuestion(answers);
        }
    }
}
