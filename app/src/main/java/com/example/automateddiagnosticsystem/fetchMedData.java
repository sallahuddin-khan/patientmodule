package com.example.automateddiagnosticsystem;

import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;

import androidx.annotation.NonNull;

public class fetchMedData {
    int time;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference presRef = db.collection("Prescriptions");
    ArrayList<String> ret = new ArrayList<>();
    //ArrayList<String> ret1 = new ArrayList<>();

    fetchMedData(int time) {
        this.time = time;

    }
    public void addDataToMorningList(String Data){
       // Log.d("ADS-1",Data);
        ret.add(Data);
       // Log.d("ADS-1",Integer.toString(ret.size()));
    }
    public void getMorningMed(String contact) {
        presRef.whereEqualTo("contact", contact).whereEqualTo("status", true).whereEqualTo("morning", true).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //Log.d("ADS",document.getData().get("medicine").toString());
                                addDataToMorningList(document.getData().get("medicine").toString());
                            }
                        } else {
                            Log.d("ADS", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    public void printArrayList(){
        for(int i =0 ;i<ret.size();i++){
            Log.d("ADS-2",ret.get(i));
        }
    }
}
