package com.example.formation12.quizz.ui.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.formation12.quizz.R;
import com.example.formation12.quizz.ui.fragments.QuestionListFragment.OnListFragmentInteractionListener;

import com.example.formation12.quizz.model.Question;
import com.squareup.picasso.Picasso;

import java.util.List;

public class QuestionRecyclerViewAdapter extends RecyclerView.Adapter<QuestionRecyclerViewAdapter.ViewHolder> {

    private  List<Question> mValues;
    private final OnListFragmentInteractionListener mListener;
    private Context parentContext;

    public QuestionRecyclerViewAdapter(List<Question> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    public void updateQuestionList(List<Question> newValues) {
        mValues = newValues;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_question, parent, false);
        parentContext = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIntitule.setText(mValues.get(position).intitule);
        Picasso.with(parentContext).load(mValues.get(position).imageAuthorUrl).into(holder.imageAuthor);
        switch (mValues.get(position).isGoodAnswer){
            case 0 :
                holder.iconQuestion.setImageResource(R.drawable.ic_more_horiz_black_24dp);
                holder.layoutToBackground.setBackgroundColor(parentContext.getResources().getColor(R.color.Gray));
                break;
            case 1 :
                holder.iconQuestion.setImageResource(R.drawable.ic_check_green);
                holder.layoutToBackground.setBackgroundColor(parentContext.getResources().getColor(R.color.LightGreen));
                break;
            case -1 :
                holder.iconQuestion.setImageResource(R.drawable.ic_close_black_24dp);
                holder.layoutToBackground.setBackgroundColor(parentContext.getResources().getColor(R.color.Red));
                break;
        }
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });

        holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(parentContext);
                dlgAlert.setMessage("Que voulez-vous faire ?");
                dlgAlert.setPositiveButton("Modifier",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //dismiss the dialog
                                mListener.editQuestion(holder.mItem);
                            }
                        });
                dlgAlert.setNegativeButton("supprimer",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mListener.deleteQuestion(holder.mItem);
                            }
                        });
                dlgAlert.setCancelable(true);
                dlgAlert.create().show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIntitule;
        public Question mItem;
        public ImageView imageAuthor;
        public RelativeLayout layoutToBackground;
        public ImageView iconQuestion;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIntitule = view.findViewById(R.id.intitule_item);
            imageAuthor = view.findViewById(R.id.image_author);
            layoutToBackground = view.findViewById(R.id.bottom_layout);
            iconQuestion = view.findViewById(R.id.icon_question);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mIntitule.getText() + "'";
        }
    }
}
