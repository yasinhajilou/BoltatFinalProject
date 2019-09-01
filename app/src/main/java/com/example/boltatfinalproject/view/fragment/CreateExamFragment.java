package com.example.boltatfinalproject.view.fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.boltatfinalproject.R;
import com.example.boltatfinalproject.model.Answer;
import com.example.boltatfinalproject.model.Exam;
import com.example.boltatfinalproject.model.Question;
import com.example.boltatfinalproject.viewmodel.AnswerViewModel;
import com.example.boltatfinalproject.viewmodel.ExamViewModel;
import com.example.boltatfinalproject.viewmodel.QuestionViewModel;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class CreateExamFragment extends Fragment {


    public CreateExamFragment() {
        // Required empty public constructor
    }


    private TextInputEditText edtExamTitle;
    private EditText edtQuestionTitle, edtFirstAn, edtSecondAn, edtThirdAn, edtFourthAn;
    private TextView txtQuestionsStatus;
    private ExamViewModel mExamViewModel;
    private QuestionViewModel mQuestionViewModel;
    private AnswerViewModel mAnswerViewModel;
    private String examTitle;
    LinearLayout linearLayout;
    RadioButton rbOne;
    RadioButton rbTwo;
    RadioButton rbThree;
    RadioButton rbFour;
    private int a = 0;
    private boolean[] answerState = new boolean[4];
    private int c = 0;
    private int d = 0;
    private long examId;
    private String questionTitle;
    private String[] answers = new String[4];

    //count questions that saved into db
    int questionCounter = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_exam, container, false);
        edtExamTitle = view.findViewById(R.id.ti_test_title);
        edtQuestionTitle = view.findViewById(R.id.et_question_title);
        edtFirstAn = view.findViewById(R.id.et_first_answer);
        edtSecondAn = view.findViewById(R.id.et_second_answer);
        edtThirdAn = view.findViewById(R.id.et_third_answer);
        edtFourthAn = view.findViewById(R.id.et_fourth_answer);
        Button btnSave = view.findViewById(R.id.btn_save_question);
        Button btnNext = view.findViewById(R.id.btn_next_question);
        txtQuestionsStatus = view.findViewById(R.id.tv_show_questions);
        linearLayout = view.findViewById(R.id.ll_question_bar);
        rbOne = view.findViewById(R.id.rb_one);
        rbTwo = view.findViewById(R.id.rb_two);
        rbThree = view.findViewById(R.id.rb_tree);
        rbFour = view.findViewById(R.id.rb_four);
        mExamViewModel = ViewModelProviders.of(getActivity()).get(ExamViewModel.class);
        mQuestionViewModel = ViewModelProviders.of(getActivity()).get(QuestionViewModel.class);
        mAnswerViewModel = ViewModelProviders.of(getActivity()).get(AnswerViewModel.class);


        txtQuestionsStatus.setText("Question: " + questionCounter);

        mExamViewModel.getExamMutableLiveData().observe(getViewLifecycleOwner(), new Observer<Long>() {
            @Override
            public void onChanged(Long aLong) {
                //prevent to running this scope in fragment start up
                if (c > 0) {
                    //now db added exam title to exam table and we have its id
                    //so we can add question with exam id
                    examId = aLong;
                    saveQuestion(examId);
                }
            }
        });
        mQuestionViewModel.getQuestionIdMutableLiveData().observe(getViewLifecycleOwner(), new Observer<Long>() {
            @Override
            public void onChanged(Long aLong) {
                //prevent to running this scope in fragment start up
                if (d > 0) {
                    saveAnswers(aLong);
                }
            }
        });



        //init booleans for checking correct answers(get RadioButton default value)
        answerState[0] = rbOne.isChecked();
        answerState[1] = rbTwo.isChecked();
        answerState[2] = rbThree.isChecked();
        answerState[3] = rbFour.isChecked();

        rbOne.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    rbTwo.setChecked(false);
                    rbThree.setChecked(false);
                    rbFour.setChecked(false);
                    answerState[0] = b;
                    answerState[1] = false;
                    answerState[2] = false;
                    answerState[3] = false;
                }
            }
        });

        rbTwo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    rbOne.setChecked(false);
                    rbThree.setChecked(false);
                    rbFour.setChecked(false);
                    answerState[0] = false;
                    answerState[1] = b;
                    answerState[2] = false;
                    answerState[3] = false;
                }
            }
        });

        rbThree.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    rbOne.setChecked(false);
                    rbTwo.setChecked(false);
                    rbFour.setChecked(false);
                    answerState[0] = false;
                    answerState[1] = false;
                    answerState[2] = b;
                    answerState[3] = false;
                }
            }
        });

        rbFour.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    rbOne.setChecked(false);
                    rbTwo.setChecked(false);
                    rbThree.setChecked(false);
                    answerState[0] = false;
                    answerState[1] = false;
                    answerState[2] = false;
                    answerState[3] = true;
                }
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                //check if this is first question of user for adding ,then save test name in db(fill exam table)
                if (a == 0) {
                    showAlertDialog(view);
                } else {
                    identityInputs(examTitle,examId);
                }
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                String msg;
                String positive;
                if (a == 0) {
                    msg = "You didn't make any Test,Are you want to leave?";
                    positive = "yes";
                } else {
                    msg = String.format("%s Test was made successfully with %d Question(s)", examTitle, questionCounter-1);
                    positive = "okay";
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext())
                        .setMessage(msg)
                        .setPositiveButton(positive, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Navigation.findNavController(view).navigate(R.id.action_createExamFragment_to_homeFragment);
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();


            }
        });
        return view;
    }

    private void showAlertDialog(final View view) {
        examTitle = edtExamTitle.getText().toString().trim();
        if (!examTitle.isEmpty()) {

            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext())

                    .setMessage("Are Confirm " + examTitle + " As Name Of Your Test?")

                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            edtExamTitle.setEnabled(false);
                            //check edit texts and then save test
                            identityInputs(examTitle,examId);
                        }
                    })

                    .setNeutralButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(view.getContext(), "Not saved", Toast.LENGTH_SHORT).show();
                        }
                    });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();

        } else {
            Toast.makeText(view.getContext(), "Please fill Test Title First!", Toast.LENGTH_LONG).show();
        }

    }

    //save test to db
    //and check all components to make sure all data inputted correctly
    private void identityInputs(String examTitle,long examId) {

        questionTitle = edtQuestionTitle.getText().toString().trim();
        answers[0] = edtFirstAn.getText().toString().trim();
        answers[1] = edtSecondAn.getText().toString().trim();
        answers[2] = edtThirdAn.getText().toString().trim();
        answers[3] = edtFourthAn.getText().toString().trim();

        if (!questionTitle.isEmpty()) {
            if (!answers[0].isEmpty() && !answers[1].isEmpty() &&
                    !answers[2].isEmpty() && !answers[3].isEmpty()) {
                int correctCounter = 0;
                int wrongCounter = 0;

                //check booleans for correct answer
                for (int i = 0; i < 4; i++) {
                    if (answerState[i]) {
                        correctCounter++;
                    } else {
                        wrongCounter++;
                    }
                }

                if (correctCounter > 1 || wrongCounter > 3) {
                    Toast.makeText(getContext(), "Please select only one Correct Answer", Toast.LENGTH_LONG).show();
                } else {
                    //every components are okay
                    //check for is this first time or not!
                    if (a == 0) {
                        saveTest(examTitle);
                        a++;
                        d++;
                        c++;
                    } else {
                        //test created before we need to save question
                        saveQuestion(examId);
                    }
                }
            } else {
                Toast.makeText(getContext(), "Please Fill All Answer Box", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getContext(), "Please Fill Question Field.", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveAnswers(long questionId) {
        Answer[] answersObj = new Answer[4];
        for (int i = 0; i < answersObj.length; i++) {
            answersObj[i] = new Answer(0,answers[i],answerState[i] ,questionId);
        }
        mAnswerViewModel.insert(answersObj);
        resetQuestionAndAnswers();
        txtQuestionsStatus.setText("Question: " + (++questionCounter));

    }

    private void saveTest(String examTitle) {
        Exam exam = new Exam(0, examTitle);
        mExamViewModel.insert(exam);
    }

    private void saveQuestion(long examId) {
        Question question = new Question(0, questionTitle, examId);
        mQuestionViewModel.insert(question);
    }

    private void resetQuestionAndAnswers() {
        Animation aniFade = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.fade_in);
        linearLayout.startAnimation(aniFade);
        edtQuestionTitle.setText("");
        edtFirstAn.setText("");
        edtSecondAn.setText("");
        edtThirdAn.setText("");
        edtFourthAn.setText("");
        rbOne.setChecked(false);
        rbTwo.setChecked(false);
        rbThree.setChecked(false);
        rbFour.setChecked(false);
    }

}
