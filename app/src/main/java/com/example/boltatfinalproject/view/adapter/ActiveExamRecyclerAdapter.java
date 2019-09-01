package com.example.boltatfinalproject.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.boltatfinalproject.R;
import com.example.boltatfinalproject.RecyclerExamsInterface;
import com.example.boltatfinalproject.model.Exam;
import com.example.boltatfinalproject.model.Question;

import java.util.ArrayList;
import java.util.List;

public class ActiveExamRecyclerAdapter extends RecyclerView.Adapter<ActiveExamRecyclerAdapter.ExamViewHolder> {
    RecyclerExamsInterface mInterface;
    List<Exam> mList;
    public void setData(List<Exam> exams , RecyclerExamsInterface examsInterface){
        mList = exams;
        mInterface = examsInterface;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ExamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exam_list_item,parent,false);
        return new ExamViewHolder(view , mInterface , mList);
    }

    @Override
    public void onBindViewHolder(@NonNull ExamViewHolder holder, int position) {
        holder.bindData(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList==null ? 0 : mList.size();
    }

     class ExamViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvExamTitle;
        RecyclerExamsInterface mInterface;
         List<Exam> mList;
         ExamViewHolder(@NonNull View itemView , RecyclerExamsInterface examsInterface , List<Exam> exams) {
            super(itemView);
            tvExamTitle = itemView.findViewById(R.id.tv_exam_list_title);
            itemView.setOnClickListener(this);
            mInterface=examsInterface;
            mList = exams;
        }

         void bindData(Exam exam){
            tvExamTitle.setText(exam.getTitle());
        }

         @Override
         public void onClick(View view) {
             mInterface.onExamClicked(mList.get(getAdapterPosition()).getId());
         }
     }
}
