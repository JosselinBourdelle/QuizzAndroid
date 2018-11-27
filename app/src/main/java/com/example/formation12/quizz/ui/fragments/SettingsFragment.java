package com.example.formation12.quizz.ui.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.example.formation12.quizz.R;


public class SettingsFragment extends Fragment {

    CheckBox check;
    boolean isCheck;

    public SettingsFragment() {
    }

    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_settings, container, false);

        check = v.findViewById(R.id.check_save);

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isCheck = check.isChecked();
                SharedPreferences mSettings = PreferenceManager.getDefaultSharedPreferences(getActivity());
                SharedPreferences.Editor editor = mSettings.edit();
                editor.putBoolean("checkSave", isCheck);
                editor.apply();
            }
        });

        SharedPreferences mSettings = PreferenceManager.getDefaultSharedPreferences(getActivity());
        isCheck = mSettings.getBoolean("checkSave", false);
        check.setChecked(isCheck);

        return v;
    }
}
