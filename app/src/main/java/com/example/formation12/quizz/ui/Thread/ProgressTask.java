package com.example.formation12.quizz.ui.Thread;

import android.os.AsyncTask;
import android.os.SystemClock;

public class ProgressTask extends AsyncTask<Void, Integer, String> {
    int count = 0;

    private OnProgressBarListener listener;

    public ProgressTask(OnProgressBarListener listener){
        super();
        this.listener =  listener;
    }

    @Override
    protected void onPreExecute() {
       listener.onBegin();
    }

    @Override
    protected void onPostExecute(String s) {
        listener.onFinish();
    }

    @Override
    protected String doInBackground(Void... params) {
        while (count < 100) {
            SystemClock.sleep(50);
            count++;
            publishProgress(count * 1);
        }
        return "Complete";
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        listener.onProgress(values);
    }

    public interface OnProgressBarListener{
        void onBegin();
        void onProgress(Integer... values);
        void onFinish();
    }

}
