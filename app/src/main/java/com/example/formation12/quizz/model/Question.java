package com.example.formation12.quizz.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Question implements Parcelable {
    public int idquestion;
    public String intitule;
    public List<String> propositions;
    public String bonneReponse;
    public int nombreMaxPropositions;
    public String imageAuthorUrl;
    public String authorName;
    public int isGoodAnswer = 0;


    public Question(String question, int nombreDeReponse) {

        this.intitule = question;
        propositions = new ArrayList<String>();
        nombreMaxPropositions = nombreDeReponse;
    }

    protected Question(Parcel in) {
        intitule = in.readString();
        propositions = in.createStringArrayList();
        bonneReponse = in.readString();
        nombreMaxPropositions = in.readInt();
        idquestion = in.readInt();
        imageAuthorUrl = in.readString();
        authorName = in.readString();
        isGoodAnswer = in.readInt();
    }

    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };

    public boolean verifierReponse(String reponsePropose) {
        return reponsePropose.equals(bonneReponse);
    }

    public void addPropositions(String nouvellePropositions) {
        if (propositions.size()<nombreMaxPropositions) {
            propositions.add(nouvellePropositions);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(intitule);
        dest.writeStringList(propositions);
        dest.writeString(bonneReponse);
        dest.writeInt(nombreMaxPropositions);
        dest.writeInt(idquestion);
        dest.writeString(imageAuthorUrl);
        dest.writeString(authorName);
        dest.writeInt(isGoodAnswer);
    }
}