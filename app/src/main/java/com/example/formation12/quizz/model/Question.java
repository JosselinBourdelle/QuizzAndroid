package com.example.formation12.quizz;

import java.util.ArrayList;
import java.util.List;

public class Question {
    public String intitule;
    public List<String> propositions;
    public String bonneReponse;
    public int nombreMaxPropositions;

    public Question(String question, int nombreDeReponse) {
        this.intitule = question;
        propositions = new ArrayList<String>();
        nombreMaxPropositions = nombreDeReponse;
    }

    public boolean verifierReponse(String reponsePropose) {
        return reponsePropose.equals(bonneReponse);
    }

    public void addPropositions(String nouvellePropositions) {
        if (propositions.size()<nombreMaxPropositions) {
            propositions.add(nouvellePropositions);
        }
    }

}