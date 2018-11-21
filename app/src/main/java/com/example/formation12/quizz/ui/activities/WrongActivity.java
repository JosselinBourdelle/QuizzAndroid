package com.example.formation12.quizz.ui.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.formation12.quizz.R;

public class WrongActivity extends AppCompatActivity {

    ImageButton wrongButton;
    ViewGroup transitionContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wrong);

        findViewById(R.id.button_wrong).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        transitionContainer = findViewById(R.id.transition_container_wrong);
        wrongButton = findViewById(R.id.button_wrong);
        wrongButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();

        final ObjectAnimator animation1 = ObjectAnimator.ofFloat(transitionContainer, "translationY", 0f);
        animation1.setDuration(1000);

        animation1.start();

        animation1.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                finish();
            }
        });


    }
}
