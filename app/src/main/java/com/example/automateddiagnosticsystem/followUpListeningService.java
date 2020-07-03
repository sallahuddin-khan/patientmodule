package com.example.automateddiagnosticsystem;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class followUpListeningService extends Service {
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference followRef = db.collection("FollowOns");
    ListenerRegistration registration;
    String contact;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }


    @Override
    public void onCreate() {
        createFollowOnNotifyChannel();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(user != null) {
            contact = user.getPhoneNumber();
        }
        Log.d("ADS-SERVICE", "contact"+contact);

        registration  = followRef.whereEqualTo("contact", contact)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@com.google.firebase.database.annotations.Nullable QuerySnapshot snapshots,
                                        @com.google.firebase.database.annotations.Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w("ADS", "listen:error", e);
                            return;
                        }

                        for (DocumentChange dc : snapshots.getDocumentChanges()) {
                            switch (dc.getType()) {
                                case MODIFIED:
                                    Log.d("ADS", "Modified city: " + dc.getDocument().getData());
                                    String requested =  dc.getDocument().getData().get("requested").toString();
                                    String time =  dc.getDocument().getData().get("time").toString();
                                    String date = dc.getDocument().getData().get("followondate").toString();
                                    String doctor = dc.getDocument().getData().get("doctorname").toString();
                                    String title ="";
                                    String body = "";
                                    if(requested == "true"){
                                        title = "Follow on accepted by "+ doctor;
                                        body = "On Date: "+ date + " at Time: "+time;

                                    }
                                    else{
                                        title = "Follow on accepted by "+ doctor;
                                        body = "on date: "+ date + " at Time: "+time;

                                    }
                                    Toast.makeText(getApplicationContext(),requested,Toast.LENGTH_SHORT).show();
                                    NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(),"followUpNot1")
                                            .setSmallIcon(R.drawable.addnew)
                                            .setContentTitle(title)
                                            .setContentText(body)
                                            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                            .setAutoCancel(true);
                                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
                                    notificationManager.notify(300,builder.build());
                                    break;
                                case REMOVED:
                                    Log.d("ADS", "Modified city: " + dc.getDocument().getData());
                                    String requested1 =  dc.getDocument().getData().get("requested").toString();
                                    String time1 =  dc.getDocument().getData().get("time").toString();
                                    String date1 = dc.getDocument().getData().get("followondate").toString();
                                    String doctor1 = dc.getDocument().getData().get("doctorname").toString();
                                    String title1 ="";
                                    String body1 = "";
                                    if(requested1 == "true"){
                                        title1 = "Follow on removed by "+ doctor1;
                                        body1 = "On Date: "+ date1 + " at Time: "+time1;

                                    }
                                    else{
                                        title1 = "Follow on removed by "+ doctor1;
                                        body1 = "on date: "+ date1 + " at Time: "+time1;

                                    }
                                    Toast.makeText(getApplicationContext(),requested1,Toast.LENGTH_SHORT).show();
                                    NotificationCompat.Builder builder1 = new NotificationCompat.Builder(getApplicationContext(),"followUpNot1")
                                            .setSmallIcon(R.drawable.addnew)
                                            .setContentTitle(title1)
                                            .setContentText(body1)
                                            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                            .setAutoCancel(true);
                                    NotificationManagerCompat notificationManager1 = NotificationManagerCompat.from(getApplicationContext());
                                    notificationManager1.notify(300,builder1.build());
                                    break;
                            }
                        }

                    }
                });
    }


    private void createFollowOnNotifyChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "followUpNotify";
            String description = "channel for Notifying changes in follow Ons";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("followUpNot1" , name ,importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
