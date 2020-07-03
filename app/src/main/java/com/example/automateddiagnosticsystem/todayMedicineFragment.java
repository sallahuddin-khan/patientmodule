package com.example.automateddiagnosticsystem;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class todayMedicineFragment extends Fragment {
    String contact;
    private RecyclerView morningRecycler;
    private RecyclerView afternoonRecycler;
    private RecyclerView eveningRecycler;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference presRef = db.collection("Prescriptions");
    private CollectionReference addRef = db.collection("medicineHistory");
    private dailyDosageAdapter adapter;
    private dailyDosageAdapter adapter1;
    private dailyDosageAdapter adapter2;
    public static final String DATE_FORMAT_1 = "dd-MM-yyyy";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        HomeScreen myActivity = (HomeScreen)getActivity();
        contact = myActivity.getActivityContact();
        //Toast.makeText(getContext(),contact , Toast.LENGTH_SHORT).show();
        return inflater.inflate(R.layout.today_med_frag, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //Toast.makeText(getContext(), contact, Toast.LENGTH_SHORT).show();
       Query query = presRef.whereEqualTo("contact",contact).whereEqualTo("status",true).whereEqualTo("morning",true);
        FirestoreRecyclerOptions<patientPrescription> options = new FirestoreRecyclerOptions.Builder<patientPrescription>()
                .setQuery(query,patientPrescription.class)
                .build();
        adapter = new dailyDosageAdapter(options);
        morningRecycler = (RecyclerView)view.findViewById(R.id.morning_recycler);
        morningRecycler.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false));
        morningRecycler.setAdapter(adapter);
        adapter.setOnItemClickListener(new dailyDosageAdapter.OnItemClickListener() {
            @Override
            public void onClick(DocumentSnapshot documentSnapshot, int position) {
                final String ids = documentSnapshot.getId();
                final patientPrescription pr = documentSnapshot.toObject(patientPrescription.class);
                SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_1);
                dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                Date today = Calendar.getInstance().getTime();
                final String todaysDay = dateFormat.format(today);
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Have You Taken the medicine");
                builder.setPositiveButton("Yes I Have", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //Toast.makeText(getContext(), "Ok", Toast.LENGTH_SHORT).show();
                        setMedicineTaken(1,todaysDay,ids,pr);
                    }
                });
                builder.setNegativeButton("No I have Not", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //Toast.makeText(getContext(), "No", Toast.LENGTH_SHORT).show();

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });


        ////////////////////////////////////////////////////////////

        Query query1 = presRef.whereEqualTo("contact",contact).whereEqualTo("status",true).whereEqualTo("afternoon",true);
        FirestoreRecyclerOptions<patientPrescription> options1 = new FirestoreRecyclerOptions.Builder<patientPrescription>()
                .setQuery(query1,patientPrescription.class)
                .build();
        adapter1 = new dailyDosageAdapter(options1);
        afternoonRecycler = (RecyclerView)view.findViewById(R.id.afternoon_recycler);
        afternoonRecycler.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false));
        afternoonRecycler.setAdapter(adapter1);
        adapter1.setOnItemClickListener(new dailyDosageAdapter.OnItemClickListener() {
            @Override
            public void onClick(DocumentSnapshot documentSnapshot, int position) {
                final String ids = documentSnapshot.getId();
                final patientPrescription pr = documentSnapshot.toObject(patientPrescription.class);
                SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_1);
                dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                Date today = Calendar.getInstance().getTime();
                final String todaysDay = dateFormat.format(today);
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Have You Taken the medicine");
                builder.setPositiveButton("Yes I Have", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //Toast.makeText(getContext(), "Ok", Toast.LENGTH_SHORT).show();
                        setMedicineTaken(2,todaysDay,ids,pr);
                    }
                });
                builder.setNegativeButton("No I have Not", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(getContext(), "No", Toast.LENGTH_SHORT).show();

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        ///////////////////////////////////////////////
        Query query2 = presRef.whereEqualTo("contact",contact).whereEqualTo("status",true).whereEqualTo("evening",true);
        FirestoreRecyclerOptions<patientPrescription> options2 = new FirestoreRecyclerOptions.Builder<patientPrescription>()
                .setQuery(query2,patientPrescription.class)
                .build();
        adapter2 = new dailyDosageAdapter(options2);
        eveningRecycler = (RecyclerView)view.findViewById(R.id.evening_recycler);
        eveningRecycler.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false));
        eveningRecycler.setAdapter(adapter2);
        adapter2.setOnItemClickListener(new dailyDosageAdapter.OnItemClickListener() {
            @Override
            public void onClick(DocumentSnapshot documentSnapshot, int position) {
                final String ids = documentSnapshot.getId();
                final patientPrescription pr = documentSnapshot.toObject(patientPrescription.class);
                SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_1);
                dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                Date today = Calendar.getInstance().getTime();
                final String todaysDay = dateFormat.format(today);
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Have You Taken the medicine");
                builder.setPositiveButton("Yes I Have", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //Toast.makeText(getContext(), "Ok", Toast.LENGTH_SHORT).show();
                        setMedicineTaken(3,todaysDay,ids,pr);
                    }
                });
                builder.setNegativeButton("No I have Not", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(getContext(), "No", Toast.LENGTH_SHORT).show();

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });



    }
    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
        adapter1.startListening();
        adapter2.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
        adapter1.stopListening();
        adapter2.stopListening();
    }

    public void setMedicineTaken(int time,  String date, String medId, patientPrescription med){
        String t="Morning";
        if(time==1) {
            t = "Morning";
        }
        if(time==2) {
            t = "Afternoon";
        }
        if(time==3) {
            t = "Evening";
        }
        final medicineRecordModel md = new medicineRecordModel(date,t,medId,med.getMedicine(),contact);
      addRef
                .whereEqualTo("patientContact", contact)
                .whereEqualTo("prescriptionid",medId)
                .whereEqualTo("dateTaken",date)
                .whereEqualTo("timeTaken",t)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            boolean isEmpty1 = task.getResult().isEmpty();
                            if(isEmpty1) {

                                addRef.add(md).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        Toast.makeText(getContext(), "Medicine market", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(getContext(), "Internet not connected", Toast.LENGTH_SHORT).show();
                                            }
                                        });

                                //Toast.makeText(getContext(), "Empty Snapshot returned", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(getContext(), "You have already taken", Toast.LENGTH_SHORT).show();
                            }


                        } else {
                            Log.d("DAAAAAAAAAAAAPPPPPPPPP", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}
