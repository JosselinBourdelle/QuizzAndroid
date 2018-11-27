package com.example.formation12.quizz.ui.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.formation12.quizz.R;
import com.example.formation12.quizz.model.Question;
import com.example.formation12.quizz.receiver.NetworkChangeReceiver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class AddFragment extends Fragment {

    //variables
    boolean isOffline = false;

    List<CheckBox> checkBoxes = new ArrayList<>();
    EditText textIntitule, textAnswer1, textAnswer2,textAnswer3,textAnswer4;
    CheckBox check1, check2, check3, check4;
    FloatingActionButton addButton;

    Question questionToEditable = null;

    public OnCreateListener onCreateListener;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add, container, false);

        textIntitule = rootView.findViewById(R.id.edittext_intitule);
        textAnswer1 = rootView.findViewById(R.id.edittext_answer1);
        textAnswer2 = rootView.findViewById(R.id.edittext_answer2);
        textAnswer3 = rootView.findViewById(R.id.edittext_answer3);
        textAnswer4 = rootView.findViewById(R.id.edittext_answer4);

        addButton = rootView.findViewById(R.id.floatbutton_add);

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

        if(questionToEditable!= null){
            setQuestionUpgrade();
        }

        rootView.findViewById(R.id.floatbutton_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Question q = new Question(textIntitule.getText().toString(),4);

                if(questionToEditable!=null){
                    q = questionToEditable;
                }
                q.propositions = Arrays.asList( textAnswer1.getText().toString(), textAnswer2.getText().toString(),
                        textAnswer3.getText().toString(), textAnswer4.getText().toString());
                q.bonneReponse = addBonneReponse();

                onCreateListener.questionCreated(q);
            }
        });
        return rootView;
    }

    public void setQuestionToEditable(Question q){
        questionToEditable = q;
    }

    public void setQuestionUpgrade(){
        textIntitule.setText(questionToEditable.intitule);
        textAnswer1.setText(questionToEditable.propositions.get(0));
        textAnswer2.setText(questionToEditable.propositions.get(1));
        textAnswer3.setText(questionToEditable.propositions.get(2));
        textAnswer4.setText(questionToEditable.propositions.get(3));
        for(CheckBox c : checkBoxes){
            c.setChecked(false);
        }
        if(questionToEditable.verifierReponse(questionToEditable.propositions.get(0))){
            check1.setChecked(true);
        } else if(questionToEditable.verifierReponse(questionToEditable.propositions.get(1))){
            check2.setChecked(true);
        }else if(questionToEditable.verifierReponse(questionToEditable.propositions.get(2))){
            check3.setChecked(true);
        }else if(questionToEditable.verifierReponse(questionToEditable.propositions.get(3))){
            check4.setChecked(true);
        }
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
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof AddFragment.OnCreateListener) {
            onCreateListener = (AddFragment.OnCreateListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onCreateListener = null;
    }

    public interface OnCreateListener {
        void questionCreated(Question q);
    }

    @Override
    public void onStart() {
        super.onStart();
        registerReceiver();
    }

    @Override
    public void onPause()
    {
        super.onPause();
        try
        {
            getActivity().unregisterReceiver(internalNetworkChangeReceiver);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        super.onDestroy();
    }

    private void registerReceiver()
    {
        try
        {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(NetworkChangeReceiver.NETWORK_CHANGE_ACTION);
            getActivity().registerReceiver(internalNetworkChangeReceiver, intentFilter);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    InternalNetworkChangeReceiver internalNetworkChangeReceiver = new InternalNetworkChangeReceiver();
    class InternalNetworkChangeReceiver extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (addButton != null){
                if(intent.getBooleanExtra("isOnline",false)){
                    addButton.setBackgroundColor(getResources().getColor(R.color.Green));
                    Toast.makeText(getContext(), "Online", Toast.LENGTH_LONG).show();
                    isOffline = false;
                }
                else {
                    if(!isOffline){
                        addButton.setBackgroundColor(getResources().getColor(R.color.Red));
                        Toast.makeText(getContext(), "Offline", Toast.LENGTH_LONG).show();
                        isOffline = true;
                    }
                }
            }
            Log.d("debug receiver", intent.getStringExtra("status"));
        }
    }
}


