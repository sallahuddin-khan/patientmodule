package com.example.automateddiagnosticsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(user != null){
            String number = user.getPhoneNumber();
            //Toast.makeText(getApplicationContext(),number, Toast.LENGTH_SHORT).show();
            Intent mainIntent = new Intent(MainActivity.this, HomeScreen.class);
            mainIntent.putExtra("Phone",number);
            startActivity(mainIntent);
        }
        setContentView(R.layout.activity_main);
        stopService(new Intent(this, followUpListeningService.class));
    }

    public void signIn(View view){
       // Toast.makeText(getApplicationContext(),"Click Successfull",Toast.LENGTH_SHORT).show();
        Intent in = new Intent(getApplicationContext(),SignUp.class);
        startActivity(in);
    }

}
