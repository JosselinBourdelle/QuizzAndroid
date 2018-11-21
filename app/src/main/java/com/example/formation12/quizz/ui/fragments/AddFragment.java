package com.example.formation12.quizz.ui.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.formation12.quizz.R;
import com.example.formation12.quizz.model.Question;
import com.example.formation12.quizz.ui.activities.MainActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class AddFragment extends Fragment {

    //variables

    List<CheckBox> checkBoxes = new ArrayList<>();
    EditText textIntitule, textAnswer1, textAnswer2,textAnswer3,textAnswer4;
    CheckBox check1, check2, check3, check4;

    public OnCreateListener onCreateListener;


    public AddFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static AddFragment newInstance(String param1, String param2) {
        AddFragment fragment = new AddFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_add, container, false);

        textIntitule = rootView.findViewById(R.id.edittext_intitule);
        textAnswer1 = rootView.findViewById(R.id.edittext_answer1);
        textAnswer2 = rootView.findViewById(R.id.edittext_answer2);
        textAnswer3 = rootView.findViewById(R.id.edittext_answer3);
        textAnswer4 = rootView.findViewById(R.id.edittext_answer4);


        textAnswer1.getError();

        check1 = rootView.findViewById(R.id.check1);
        check2 = rootView.findViewById(R.id.check2);
        check3 = rootView.findViewById(R.id.check3);
        check4 = rootView.findViewById(R.id.check4);
        checkBoxes.add(check1);
        checkBoxes.add(check2);
        checkBoxes.add(check3);
        checkBoxes.add(check4);
        check1.setOnClickListener(onCheckListener);
        check2.setOnClickListener(onCheckListener);
        check3.setOnClickListener(onCheckListener);
        check4.setOnClickListener(onCheckListener);

        rootView.findViewById(R.id.floatbutton_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Question q = new Question(textIntitule.getText().toString(),4);
                q.propositions = Arrays.asList( textAnswer1.getText().toString(), textAnswer2.getText().toString(),
                                                textAnswer3.getText().toString(), textAnswer4.getText().toString());
                q.bonneReponse = addBonneReponse();
                onCreateListener.questionCreated(q);
            }
        });
        return rootView;
    }

    private String addBonneReponse(){
        if(check1.isChecked()){
            return textAnswer1.getText().toString();
        }else if (check2.isChecked()){
            return textAnswer2.getText().toString();
        }else if (check3.isChecked()){
            return textAnswer3.getText().toString();
        }else if (check4.isChecked()){
            return textAnswer4.getText().toString();
        }
        else{
            return "";
        }
    }

    private View.OnClickListener onCheckListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            CheckBox checkV = (CheckBox)v;
            for (CheckBox c : checkBoxes){
                if(!c.equals(checkV)){
                    c.setChecked(false);
                }
            }
            checkV.setChecked(true);
        }
    };

    @Override
    public void onDetach() {
        super.onDetach();
        onCreateListener = null;
    }

    public interface OnCreateListener {
        void questionCreated(Question q);// pass any parameter in your onCallBack which you want to return
    }
}


