package com.example.formation12.quizz.ui.fragments;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.formation12.quizz.R;
import com.example.formation12.quizz.database.QuestionDatabaseHelper;
import com.example.formation12.quizz.model.Question;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;

import java.util.ArrayList;
import java.util.List;

public class ScoreFragment extends Fragment {

    PieChart pieChart;
    BarChart barChart;

    public ScoreFragment() {
    }

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
        View v = inflater.inflate(R.layout.fragment_score, container, false);

        // pieChart configure ->
        pieChart = v.findViewById(R.id.piechart);
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5, 10, 5, 5);

        pieChart.setDragDecelerationFrictionCoef(0.95f);

        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);

        pieChart.setTransparentCircleColor(Color.WHITE);
        pieChart.setTransparentCircleAlpha(110);

        pieChart.setHoleRadius(10f);
        pieChart.setTransparentCircleRadius(61f);

        pieChart.setRotationAngle(0);
        pieChart.setRotationEnabled(true);

        pieChart.animateY(1400, Easing.EaseInOutQuad);

        Legend l = pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setEntryLabelTextSize(15f);

        updatePieChart();

        // barChart configure ->


        return v;
    }

    private void updatePieChart() {

        int[] listValuesAnswer = generateValuesAnswer();

        int correctAnswersCount = listValuesAnswer[0];
        int wrongAnswersCount = listValuesAnswer[1];
        int unansweredQuestionCount = listValuesAnswer[2];

        int total = correctAnswersCount + wrongAnswersCount + unansweredQuestionCount;

        ArrayList<PieEntry> questionEntries = new ArrayList<>();

        questionEntries.add(new PieEntry((float)unansweredQuestionCount/(float)(total),"A faire"));
        questionEntries.add(new PieEntry((float)wrongAnswersCount/(float)(total),"Mauvaises réponses"));
        questionEntries.add(new PieEntry((float)correctAnswersCount/(float)(total),"Bonnes réponses"));


        PieDataSet dataSet = new PieDataSet(questionEntries, "");

        dataSet.setSliceSpace(1f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);

        ArrayList<Integer> colors = new ArrayList<>();

        colors.add(Color.LTGRAY);
        colors.add(Color.RED);
        colors.add(Color.GREEN);

        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(18f);
        data.setValueTextColor(Color.BLACK);
        pieChart.setData(data);

        pieChart.highlightValues(null);

        pieChart.invalidate();
    }

    public int[] generateValuesAnswer(){
        int bonneReponse = 0;
        int mauvaiseReponse = 0;
        int aFaire = 0;
        List<Question> questions = QuestionDatabaseHelper.getInstance(getActivity()).getAllQuestions();
        for(Question q : questions){
            switch (q.isGoodAnswer){
                case 0 :
                    aFaire++;
                    break;
                case 1 :
                    bonneReponse++;
                    break;
                case -1 :
                    mauvaiseReponse++;
                    break;
            }
        }
        return new int[]{bonneReponse, mauvaiseReponse, aFaire};
    }
}
