package com.example.formation12.quizz;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class QuestionActivity extends AppCompatActivity {

    List<Question> questions = new ArrayList<Question>();

    Button answer1, answer2, answer3, answer4;
    TextView textQuestion;
    public static int countQuestionMoment = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        initQuestion();

        findViewById(R.id.button_float).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        answer1 = findViewById(R.id.answer1);
        answer2 = findViewById(R.id.answer2);
        answer3 = findViewById(R.id.answer3);
        answer4 = findViewById(R.id.answer4);
        textQuestion = findViewById(R.id.textQuestion);

        View.OnClickListener onAnswerclicked = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button buttonClicked = (Button)v;
                if(buttonClicked.getText().toString().equals(questions.get(countQuestionMoment).bonneReponse)){
                    Intent intent = new Intent(QuestionActivity.this, RightActivity.class);
                    MainActivity.score++;
                    startActivity(intent);
                }
                else{
                    Intent intent = new Intent(QuestionActivity.this, WrongActivity.class);
                    startActivity(intent);
                }
            }
        };

        answer1.setOnClickListener(onAnswerclicked);
        answer2.setOnClickListener(onAnswerclicked);
        answer3.setOnClickListener(onAnswerclicked);
        answer4.setOnClickListener(onAnswerclicked);

        setQuestion(countQuestionMoment);
        setupWindowAnimations();


    }

    private void setupWindowAnimations() {
        Slide slide = new Slide();
        slide.setDuration(1000);
        getWindow().setExitTransition(slide);
    }

    private void initQuestion() {

        Question question1 = new Question("Quel est la capitale de la france ?",4);
        question1.addPropositions("Paris");
        question1.addPropositions("Nantes");
        question1.addPropositions("Berlin");
        question1.addPropositions("Tokyo");
        question1.bonneReponse = "Nantes";
        questions.add(question1);
    }


    private void setQuestion(int count){
        Question questionMoment = questions.get(count);
        textQuestion.setText(questionMoment.intitule);
        answer1.setText(questionMoment.propositions.get(0));
        answer2.setText(questionMoment.propositions.get(1));
        answer3.setText(questionMoment.propositions.get(2));
        answer4.setText(questionMoment.propositions.get(3));
    }



}
