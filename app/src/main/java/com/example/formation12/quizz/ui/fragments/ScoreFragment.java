package com.example.formation12.quizz.ui.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.formation12.quizz.R;
import com.example.formation12.quizz.ui.activities.MainActivity;



public class ScoreFragment extends Fragment {


    public ScoreFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static ScoreFragment newInstance(String param1, String param2) {
        ScoreFragment fragment = new ScoreFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_score, container, false);

        TextView textScore = rootView.findViewById(R.id.text_score);
        textScore.setText(MainActivity.score+"");

        return rootView;
    }

}
