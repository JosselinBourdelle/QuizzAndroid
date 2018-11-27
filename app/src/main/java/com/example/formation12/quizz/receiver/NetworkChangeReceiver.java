package com.example.formation12.quizz.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;


public class NetworkChangeReceiver  extends BroadcastReceiver {

    public static final String NETWORK_CHANGE_ACTION = "com.example.formation12.quizz.NetworkChangeReceiver";

    @Override
    public void onReceive(final Context context, final Intent intent) {

        if (isOnline(context))
        {
            sendInternalBroadcast(context, "Internet Connected");
        }
        else
        {
            sendInternalBroadcast(context, "Internet Not Connected");
        }
    }

    private void sendInternalBroadcast(Context context, String status)
    {
        Log.e("BRAODCAST REVEIVER ", String.valueOf(isOnline(context)));
        try
        {
            boolean isOnline = isOnline(context);
            Intent intent = new Intent();

            intent.putExtra("isOnline", isOnline);
            intent.putExtra("status", status);
            intent.setAction(NETWORK_CHANGE_ACTION);
            context.sendBroadcast(intent);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public boolean isOnline(Context context)
    {
        boolean isOnline = false;
        try
        {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            isOnline = (netInfo != null && netInfo.isConnected());
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return isOnline;
    }
}