package com.example.automateddiagnosticsystem;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.TaskStackBuilder;

public class morningAlarmBroadcast extends BroadcastReceiver {
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent resultIntent = new Intent(context, MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntentWithParentStack(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);


        String con = intent.getStringExtra("mp");
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"morningAlarm1")
                .setSmallIcon(R.drawable.addnew)
                .setContentTitle("Morning Medicine Time")
                .setContentText("Time to take morning medicine")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(resultPendingIntent)
                .setAutoCancel(true);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(200,builder.build());
        Log.d("ADS","MORNING ALARM");
        //fetchMedData m = new fetchMedData(1);
        //m.getMorningMed(con);
        //m.printArrayList();
        //String listString = String.join(", ", r);
        //Log.d("ADS",listString);


    }
}
