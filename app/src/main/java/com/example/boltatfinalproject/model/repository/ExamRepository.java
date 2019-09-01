package com.example.boltatfinalproject.model.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.boltatfinalproject.ExamInterface;
import com.example.boltatfinalproject.model.Exam;
import com.example.boltatfinalproject.model.database.AppDatabase;
import com.example.boltatfinalproject.model.database.ExamDao;

import java.util.List;

public class ExamRepository {
    private ExamDao mDao;
    private LiveData<List<Exam>> mAllData;

    public ExamRepository(Application application) {
        AppDatabase appDatabase = AppDatabase.getDatabase(application);
        mDao = appDatabase.examDao();
        mAllData = mDao.getAllExams();
    }

    public void insert(Exam exam, ExamInterface examInterface){
        new InsertExamAsyncTask(mDao,examInterface).execute(exam);
    }
    public LiveData<List<Exam>> getAllData() {
        return mAllData;
    }

    class InsertExamAsyncTask extends AsyncTask<Exam,Void,Void>{
        ExamDao mDao;
        ExamInterface mInterface;
        public InsertExamAsyncTask(ExamDao dao,ExamInterface examInterface) {
            mDao = dao;
            mInterface = examInterface;
        }

        @Override
        protected Void doInBackground(Exam... exams) {
            long examId = mDao.insert(exams[0]);
            if (examId>0){
                mInterface.examSavedSuccessfully(examId);

            }
            return null;
        }
    }
}
