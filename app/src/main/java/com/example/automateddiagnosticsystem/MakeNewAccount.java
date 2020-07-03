package com.example.automateddiagnosticsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class MakeNewAccount extends AppCompatActivity {
    EditText username;
    EditText age;
    EditText phoneNumber;
    EditText gender;
    EditText hospital;
    EditText doctorName;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference patientRef = db.collection("PatientContacts");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_new_account);
    }

    public void SignUp(View view){
        username = findViewById(R.id.username);
        age = findViewById(R.id.age);
        phoneNumber = findViewById(R.id.phonenumber);
        gender = findViewById(R.id.gender);
        hospital =findViewById(R.id.hospital);
        doctorName =findViewById(R.id.doctorname);

        String phone_number = phoneNumber.getText().toString();

                patientRef
                .whereEqualTo("contact", phone_number).limit(1)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                           boolean isEmpty1 = task.getResult().isEmpty();
                           if(isEmpty1) {
                               String user_name = username.getText().toString();
                               String user_age = age.getText().toString();;
                               String phone_number = phoneNumber.getText().toString();
                               String user_hospital = hospital.getText().toString();
                               String user_gender = gender.getText().toString();
                               String doctor_name = doctorName.getText().toString();
                               PatientContacts pt = new PatientContacts(user_gender,phone_number,user_name);

                               Toast.makeText(getApplicationContext(), "Contact Does Not Exists", Toast.LENGTH_SHORT).show();
                               db.collection("PatientContacts").add(pt).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                   @Override
                                   public void onSuccess(DocumentReference documentReference) {
                                       Toast.makeText(getApplicationContext(), "DATA ADDED", Toast.LENGTH_SHORT).show();
                                   }
                               })
                                       .addOnFailureListener(new OnFailureListener() {
                                           @Override
                                           public void onFailure(@NonNull Exception e) {
                                               Toast.makeText(getApplicationContext(), "DATA NOT ADDED", Toast.LENGTH_SHORT).show();
                                           }
                                       });
                           }
                           else
                               Toast.makeText(getApplicationContext(),"Contact Already Exists",Toast.LENGTH_SHORT).show();

                        } else {
                            Log.d("DAAAAAAAAAAAAPPPPPPPPP", "Error getting documents: ", task.getException());
                        }
                    }
                });







    }
}
