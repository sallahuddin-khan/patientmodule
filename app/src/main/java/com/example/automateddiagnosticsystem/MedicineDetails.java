package com.example.automateddiagnosticsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MedicineDetails extends AppCompatActivity {
    String mid;
    String contact;
    TextView medicineName;
    TextView medicineDoctor;
    TextView morning;
    TextView evening;
    TextView afternoon;
    TextView active;
    TextView dosage;
    TextView instructions;
    Switch notify;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference medRef = db.collection("Prescriptions");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_details);

        mid= getIntent().getStringExtra("MID");
        contact = getIntent().getStringExtra("contact");
        medicineName=findViewById(R.id.det_med_name);
        medicineDoctor=findViewById(R.id.det_med_doctor);
        morning=findViewById(R.id.det_med_morning);
        evening=findViewById(R.id.det_med_evening);
        afternoon=findViewById(R.id.det_med_afternoon);
        active=findViewById(R.id.det_med_active);
        notify = findViewById(R.id.switch1);
        dosage = findViewById(R.id.det_med_dosage);
        instructions = findViewById(R.id.det_med_instructions);
        patientPrescription p = new patientPrescription();
        medRef.document(mid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        patientPrescription p = document.toObject(patientPrescription.class );
                        medicineName.setText(p.getMedicine());
                        medicineDoctor.setText(p.getDoctor());
                        dosage.setText(p.getDosage());
                        instructions.setText(p.getInstructions());
                        if(p.isMorning())
                            morning.setText("Morning");

                        else
                            morning.setText(" ");
                        if(p.isEvening())
                            evening.setText("Evening");

                        else
                            evening.setText(" ");
                        if(p.isAfternoon())
                            afternoon.setText("Afternoon");

                        else
                            afternoon.setText(" ");
                        if(p.isStatus())
                            active.setText("Active");

                        else
                            active.setText(" ");

                        if(p.isNotify())
                            notify.setChecked(true);
                        else
                            notify.setChecked(false);
                    } else {
                        Log.d("AAAAAAAAAAAAAAA", "No such document");
                    }
                } else {
                    Log.d("AAAAAAAAAAAAA", "get failed with ", task.getException());
                }
            }
        });
        notify.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    medRef.document(mid).update("notify",true);
                }
                else{
                    medRef.document(mid).update("notify",false);
                }

            }
        });

    }
}
