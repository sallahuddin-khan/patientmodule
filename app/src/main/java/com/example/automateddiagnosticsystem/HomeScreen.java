package com.example.automateddiagnosticsystem;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.automateddiagnosticsystem.ui.main.SectionsPagerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Calendar;

public class HomeScreen extends AppCompatActivity {
    String contact;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String MORNING = "morning";
    AlarmManager alarmManager;
    PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        contact = getIntent().getStringExtra("Phone");
        if(!checkMoriningAlarm()) {
            createAlarmChannel();
            startAlarm();
            startService(new Intent(this, followUpListeningService.class));

        }


    }
    public void startAlarm(){
        Toast.makeText(this,"reminder started", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(HomeScreen.this , morningAlarmBroadcast.class);
        intent.putExtra("mp",contact);
        pendingIntent = PendingIntent.getBroadcast(HomeScreen.this,0,intent,0);

        alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);

        long timeAtClick = System.currentTimeMillis();
        long secondInMillis = 1000*10;


        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 9);
        calendar.set(Calendar.MINUTE, 10);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);
        saveReminderStatus(true);
    }
    private void createAlarmChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "morningAlarm";
            String description = "channel for morining Alarm";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("morningAlarm1" , name ,importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public String getActivityContact(){
        return contact;
    }

    public void signOut(View v){
        //stopService(new Intent(this, followUpListeningService.class));
        saveReminderStatus(false);
        if (alarmManager!= null) {
            alarmManager.cancel(pendingIntent);
            Log.d("ADS-CANCEL ALARM","ALARM CANCELED");
        }

        FirebaseAuth.getInstance().signOut();
        Intent mainIntent = new Intent(HomeScreen.this, MainActivity.class);
        startActivity(mainIntent);
        finish();
    }
    private void saveReminderStatus(boolean status){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(MORNING, status);
        editor.apply();
        Log.d("ADS-HomeScreen","shared preference set");
    }
    private boolean checkMoriningAlarm(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        return sharedPreferences.getBoolean(MORNING, false);

    }
}