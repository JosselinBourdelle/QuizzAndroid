package com.example.formation12.quizz;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.formation12.quizz.model.Question;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class QuestionListFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public QuestionListFragment() {
    }

    // TODO: Customize parameter initialization
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //TODO: list question
        List<Question> questions = new ArrayList<>();
        questionsInit(questions);


        View view = inflater.inflate(R.layout.fragment_question_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new QuestionRecyclerViewAdapter(questions, mListener));
        }
        return view;
    }

    public void questionsInit(List<Question> list){
        Question question1 = new Question("Quelle est l'animal le plus grand du monde ?", 4);
        question1.propositions = Arrays.asList(new String[]{"La baleine bleue", "La méduse à crinière de lion", "Lineus longissimus", "Godzilla"});
        question1.bonneReponse = "La méduse à crinière de lion";

        Question question2 = new Question("Quelle est le poison le plus puissant du monde ?", 4);
        question2.propositions = Arrays.asList(new String[]{"Botox", "Cyanure", "Hydrophis-belcheri", "Sirop pour la toux"});
        question2.bonneReponse = "Botox";

        Question question3 = new Question("Les araignès sont-elles des insectes ?", 4);
        question3.propositions = Arrays.asList(new String[]{"oui", "non", "On est pas sûr", "La réponse D"});
        question3.bonneReponse = "non";

        Question question4 = new Question("Combien de fois peut-on plier une feuille de papier au maximum ?", 4);
        question4.propositions = Arrays.asList(new String[]{"7", "8", "15 ou 16", "au moins 8000 !"});
        question4.bonneReponse = "15 ou 16";

        list.add(question1);
        list.add(question2);
        list.add(question3);
        list.add(question4);
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Question item);


    }
}
