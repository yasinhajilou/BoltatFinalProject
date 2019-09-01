package com.example.boltatfinalproject.view.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.boltatfinalproject.AnswerInterface;
import com.example.boltatfinalproject.R;
import com.example.boltatfinalproject.model.Answer;
import com.example.boltatfinalproject.model.Question;
import com.example.boltatfinalproject.viewmodel.AnswerViewModel;
import com.example.boltatfinalproject.viewmodel.QuestionViewModel;

import java.util.List;


public class TakeExamFragment extends Fragment implements AnswerInterface {
    private QuestionViewModel mQuestionViewModel;
    private long examId;
    List<Question> mQuestions;
    TextView tvTimer, tvTitle , tvQuestionSit;
    RadioGroup mRadioGroup;
    RadioButton rbOne, rbTwo, rbThree, rbFour;
    Button next, exit;
    int currentQuestionPosition = 0;
    AnswerViewModel mAnswerViewModel;
    List<Answer> mAnswers;
    int wrong = 0;
    int correct = 0;
    CountDownTimer mCountDownTimer;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_take_exam, container, false);
        // Inflate the layout for this fragment
        if (getArguments() != null) {
            examId = getArguments().getLong("KEY_EXAM_ID");
        }
        tvTimer = view.findViewById(R.id.tv_timer);
        tvTitle = view.findViewById(R.id.tv_question_title);
        mRadioGroup = view.findViewById(R.id.rbg_take_exam);
        next = view.findViewById(R.id.btn_next_exam);
        exit = view.findViewById(R.id.btn_exit_exam);
        rbOne = view.findViewById(R.id.rb_one_take_exame);
        rbTwo = view.findViewById(R.id.rb_two_take_exame);
        rbThree = view.findViewById(R.id.rb_three_take_exame);
        rbFour = view.findViewById(R.id.rb_four_take_exame);
        tvQuestionSit = view.findViewById(R.id.tv_question_situation_take);


        mQuestionViewModel = ViewModelProviders.of(this).get(QuestionViewModel.class);
        mAnswerViewModel = ViewModelProviders.of(this).get(AnswerViewModel.class);

        mQuestionViewModel.getDataForExam(examId).observe(getViewLifecycleOwner(), new Observer<List<Question>>() {
            @Override
            public void onChanged(List<Question> questions) {
                mQuestions = questions;
                tvTitle.setText(questions.get(currentQuestionPosition).getTitle());
                mAnswerViewModel.getDataForQuestion(questions.get(currentQuestionPosition).getId(), TakeExamFragment.this);
                currentQuestionPosition++;
                tvQuestionSit.setText(currentQuestionPosition+"of " + mQuestions.size());

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedId = mRadioGroup.getCheckedRadioButtonId();
                if (selectedId == -1) {
                    Toast.makeText(getContext(), "Please Select One Answer", Toast.LENGTH_SHORT).show();
                } else {
                    switch (selectedId) {
                        case R.id.rb_one_take_exame:
                            if (mAnswers.get(0).getState()) {
                                correct++;
                            } else {
                                wrong++;
                            }
                            break;
                        case R.id.rb_two_take_exame:
                            if (mAnswers.get(1).getState()) {
                                correct++;
                            } else {
                                wrong++;
                            }
                            break;
                        case R.id.rb_three_take_exame:
                            if (mAnswers.get(2).getState()) {
                                correct++;
                            } else {
                                wrong++;
                            }
                            break;
                        case R.id.rb_four_take_exame:
                            if (mAnswers.get(3).getState()) {
                                correct++;
                            } else {
                                wrong++;
                            }
                            break;
                        default:
                            Toast.makeText(getContext(), "Please select One Answer", Toast.LENGTH_SHORT).show();

                    }

                    if (currentQuestionPosition == mQuestions.size()) {
                        Toast.makeText(getContext(), "Questions finished", Toast.LENGTH_SHORT).show();
                        if (mCountDownTimer != null) {
                            mCountDownTimer.cancel();
                        }
                        rbOne.setEnabled(false);
                        rbThree.setEnabled(false);
                        rbTwo.setEnabled(false);
                        rbFour.setEnabled(false);

                        showDialog();

                    } else {
                        tvTitle.setText(mQuestions.get(currentQuestionPosition).getTitle());
                        mAnswerViewModel.getDataForQuestion(mQuestions.get(currentQuestionPosition).getId(), TakeExamFragment.this);
                        currentQuestionPosition++;
                        if (mCountDownTimer != null) {
                            mCountDownTimer.cancel();
                            mCountDownTimer.start();
                        }
                    }
                }

                rbOne.setChecked(false);
                rbThree.setChecked(false);
                rbTwo.setChecked(false);
                rbFour.setChecked(false);
                tvQuestionSit.setText(currentQuestionPosition+"of " + mQuestions.size());

            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(getView()).navigate(R.id.action_takeExamFragment_to_homeFragment);
            }
        });
        return view;
    }


    @Override
    public void AnswersForQuestion(List<Answer> answers) {
        mAnswers = answers;
        rbOne.setText(answers.get(0).getTitle());
        rbTwo.setText(answers.get(1).getTitle());
        rbThree.setText(answers.get(2).getTitle());
        rbFour.setText(answers.get(3).getTitle());
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
            mCountDownTimer.start();
        } else {
            mCountDownTimer = new CountDownTimer(10000, 1000) {

                public void onTick(long millisUntilFinished) {
                    tvTimer.setText("seconds remaining: " + millisUntilFinished / 1000);
                }

                public void onFinish() {
                    if (currentQuestionPosition == mQuestions.size()) {
                        rbOne.setEnabled(false);
                        rbThree.setEnabled(false);
                        rbTwo.setEnabled(false);
                        rbFour.setEnabled(false);
                        tvQuestionSit.setText(currentQuestionPosition+"of " + mQuestions.size());
                        wrong++;
                        showDialog();
                    } else {
                        tvQuestionSit.setText(++currentQuestionPosition+"of " + mQuestions.size());
                        tvTitle.setText(mQuestions.get(currentQuestionPosition).getTitle());
                        mAnswerViewModel.getDataForQuestion(mQuestions.get(currentQuestionPosition).getId(), TakeExamFragment.this);
                        currentQuestionPosition++;
                        wrong++;
                    }
                }
            }.start();
        }
    }

    private void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setMessage("Your Correct Answer : " + correct+"\n" + "Your Wrong Answer: "+ wrong)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Navigation.findNavController(getView()).navigate(R.id.action_takeExamFragment_to_homeFragment);
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
