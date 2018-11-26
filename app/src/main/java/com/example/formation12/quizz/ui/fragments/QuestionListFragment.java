package com.example.formation12.quizz.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.formation12.quizz.R;
import com.example.formation12.quizz.api.APIClient;
import com.example.formation12.quizz.database.QuestionDatabaseHelper;
import com.example.formation12.quizz.model.Question;

import java.util.ArrayList;
import java.util.List;


public class QuestionListFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private QuestionRecyclerViewAdapter adapter;

    public QuestionListFragment() {
    }

    @SuppressWarnings("unused")
    public static QuestionListFragment newInstance(int columnCount) {
        QuestionListFragment fragment = new QuestionListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        APIClient.getInstance().getQuestions(new APIClient.APIResult<List<Question>>() {
            @Override
            public void onFailure(Exception e) {
                Log.e("Debug : ", "FAIL !!!");
            }

            @Override
            public void OnSuccess(List<Question> object)  {

                final List<Question> dataToReaload = QuestionDatabaseHelper.getInstance(QuestionListFragment.this.getContext()).getAllQuestions();

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if( adapter != null) {
                            adapter.updateQuestionList(dataToReaload);
                        }
                    }
                });
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        List<Question> questions = new ArrayList<>();
        questions = QuestionDatabaseHelper.getInstance(getContext()).getAllQuestions();

        View view = inflater.inflate(R.layout.fragment_question_list, container, false);
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            adapter = new QuestionRecyclerViewAdapter(questions, mListener);
            recyclerView.setAdapter(adapter);
        }
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(Question item);
        void editQuestion(Question q);
        void deleteQuestion(Question q);
    }
}
