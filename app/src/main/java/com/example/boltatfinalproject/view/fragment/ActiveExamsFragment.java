package com.example.boltatfinalproject.view.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.boltatfinalproject.R;
import com.example.boltatfinalproject.RecyclerExamsInterface;
import com.example.boltatfinalproject.model.Exam;
import com.example.boltatfinalproject.view.adapter.ActiveExamRecyclerAdapter;
import com.example.boltatfinalproject.viewmodel.ExamViewModel;

import java.util.List;


public class ActiveExamsFragment extends Fragment implements RecyclerExamsInterface {

    RecyclerView mRecyclerView;
    ExamViewModel mExamViewModel;
    ActiveExamRecyclerAdapter mAdapter;
    List<Exam> mList;
    View mView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_active_exams, container, false);
        mRecyclerView = view.findViewById(R.id.rv_active_exams);
        mExamViewModel = ViewModelProviders.of(getActivity()).get(ExamViewModel.class);

        mAdapter = new ActiveExamRecyclerAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mExamViewModel.getAllData().observe(getViewLifecycleOwner(), new Observer<List<Exam>>() {
            @Override
            public void onChanged(List<Exam> exams) {
                mList = exams;
                mAdapter.setData(exams, ActiveExamsFragment.this);
            }
        });
        mView = view;
        return view;
    }

    @Override
    public void onExamClicked(long l) {
        Bundle bundle = new Bundle();
        bundle.putLong("KEY_EXAM_ID" , l);
        Navigation.findNavController(mView).navigate(R.id.action_activeExamsFragment_to_takeExamFragment,bundle);
    }
}
