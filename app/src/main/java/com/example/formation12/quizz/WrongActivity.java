package com.example.formation12.quizz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class WrongActivity extends AppCompatActivity {

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

    }
}
