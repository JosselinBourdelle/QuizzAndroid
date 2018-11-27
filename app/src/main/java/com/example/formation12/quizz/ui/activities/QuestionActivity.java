package com.example.formation12.quizz.ui.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Slide;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.formation12.quizz.database.QuestionDatabaseHelper;
import com.example.formation12.quizz.model.Question;
import com.example.formation12.quizz.R;
import com.example.formation12.quizz.ui.thread.ProgressTask;

public class QuestionActivity extends AppCompatActivity implements ProgressTask.OnProgressBarListener{

    Question questionMoment;
    double count = 0.0;

    Button answer1, answer2, answer3, answer4;
    TextView textQuestion;
    ImageView imageRight, imageWrong;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        progressBar = findViewById(R.id.progressBar);

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

                imageRight = findViewById(R.id.image_right);
                imageWrong = findViewById(R.id.image_wrong);

                if(questionMoment.verifierReponse(buttonClicked.getText().toString())){
                    animateRWImage(imageRight, 1);
                }
                else{
                    animateRWImage(imageWrong, -1);
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



    private void animateRWImage(final ImageView img, final int response){
        final Animation animScale = AnimationUtils.loadAnimation(QuestionActivity.this, R.anim.scale);
        animScale.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) { }
            @Override
            public void onAnimationEnd(Animation animation) {
                img.setVisibility(View.INVISIBLE);
                questionMoment.isGoodAnswer = response;
                questionMoment.responseTime = count;
                QuestionDatabaseHelper.getInstance(QuestionActivity.this).updateUserResponse(questionMoment);
                finish();
            }
            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        img.startAnimation(animScale);
        img.setVisibility(View.VISIBLE);
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

        animation8.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                ProgressTask pgt = new ProgressTask(QuestionActivity.this);
                pgt.execute();
            }
        });
    }

    private void setupWindowAnimations() {
        Slide slide = new Slide();
        slide.setDuration(1000);
        getWindow().setExitTransition(slide);
    }

    private void setQuestion(Question question){

        textQuestion.setText(question.intitule);
        answer1.setText(question.propositions.get(0));
        answer2.setText(question.propositions.get(1));
        answer3.setText(question.propositions.get(2));
        answer4.setText(question.propositions.get(3));
    }

    @Override
    public void onBegin() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onProgress(Integer... values) {
        count = (values[0]*5)/100;
        progressBar.setProgress(values[0]);
    }

    @Override
    public void onFinish(int count) {

        finish();
    }
}
