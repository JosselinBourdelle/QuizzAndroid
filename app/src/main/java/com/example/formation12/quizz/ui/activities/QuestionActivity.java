package com.example.formation12.quizz.ui.activities;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Slide;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.formation12.quizz.model.Question;
import com.example.formation12.quizz.R;
import com.example.formation12.quizz.ui.activities.RightActivity;
import com.example.formation12.quizz.ui.activities.WrongActivity;

import java.util.ArrayList;
import java.util.List;

public class QuestionActivity extends AppCompatActivity {

    List<Question> questions = new ArrayList<Question>();

    Question questionMoment;

    Button answer1, answer2, answer3, answer4;
    TextView textQuestion;
    public static int countQuestionMoment = 0;
    ImageView imageRight, imageWrong;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        questionMoment = getIntent().getParcelableExtra("item");

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
                if(buttonClicked.getText().toString().equals(questionMoment.bonneReponse)){
                    //Intent intent = new Intent(QuestionActivity.this, RightActivity.class);
                    MainActivity.score++;
                    //startActivity(intent);
                    imageRight = findViewById(R.id.image_right);
                    final Animation animScale = AnimationUtils.loadAnimation(QuestionActivity.this, R.anim.scale);
                    animScale.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) { }
                        @Override
                        public void onAnimationEnd(Animation animation) {
                            imageRight.setVisibility(View.INVISIBLE);
                            finish();
                        }
                        @Override
                        public void onAnimationRepeat(Animation animation) {}
                    });
                    imageRight.startAnimation(animScale);
                    imageRight.setVisibility(View.VISIBLE);
                }
                else{
                    //Intent intent = new Intent(QuestionActivity.this, WrongActivity.class);
                    //startActivity(intent);

                    imageWrong = findViewById(R.id.image_wrong);
                    final Animation animScale = AnimationUtils.loadAnimation(QuestionActivity.this, R.anim.scale);
                    animScale.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) { }
                        @Override
                        public void onAnimationEnd(Animation animation) {
                            imageWrong.setVisibility(View.INVISIBLE);
                            finish();
                        }
                        @Override
                        public void onAnimationRepeat(Animation animation) {}
                    });
                    imageWrong.startAnimation(animScale);
                    imageWrong.setVisibility(View.VISIBLE);


                }
            }
        };

        answer1.setOnClickListener(onAnswerclicked);
        answer2.setOnClickListener(onAnswerclicked);
        answer3.setOnClickListener(onAnswerclicked);
        answer4.setOnClickListener(onAnswerclicked);

        setQuestion(questionMoment);
        setupWindowAnimations();
        animateAnswer();

    }


    private void animateAnswer(){

        final ObjectAnimator animation = ObjectAnimator.ofFloat(textQuestion, "translationX", 2000f);
        animation.setDuration(0).start();

        final ObjectAnimator animation0 = ObjectAnimator.ofFloat(textQuestion, "translationX", 0f);
        animation0.setDuration(1000).start();

        final ObjectAnimator animation1 = ObjectAnimator.ofFloat(answer1, "translationX", 2000f);
        animation1.setDuration(0).start();

        final ObjectAnimator animation2 = ObjectAnimator.ofFloat(answer1, "translationX", 0f);
        animation2.setDuration(1500).start();

        final ObjectAnimator animation3 = ObjectAnimator.ofFloat(answer2, "translationX", 2000f);
        animation3.setDuration(0).start();

        final ObjectAnimator animation4 = ObjectAnimator.ofFloat(answer2, "translationX", 0f);
        animation4.setDuration(2000).start();

        final ObjectAnimator animation5 = ObjectAnimator.ofFloat(answer3, "translationX", 2000f);
        animation5.setDuration(0).start();

        final ObjectAnimator animation6 = ObjectAnimator.ofFloat(answer3, "translationX", 0f);
        animation6.setDuration(2500).start();

        final ObjectAnimator animation7 = ObjectAnimator.ofFloat(answer4, "translationX", 2000f);
        animation7.setDuration(0).start();

        final ObjectAnimator animation8 = ObjectAnimator.ofFloat(answer4, "translationX", 0f);
        animation8.setDuration(3000).start();
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


    private void setQuestion(Question question){

        textQuestion.setText(question.intitule);
        answer1.setText(question.propositions.get(0));
        answer2.setText(question.propositions.get(1));
        answer3.setText(question.propositions.get(2));
        answer4.setText(question.propositions.get(3));
    }



}
