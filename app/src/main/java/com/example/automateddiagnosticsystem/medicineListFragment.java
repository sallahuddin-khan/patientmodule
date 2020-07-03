package com.example.automateddiagnosticsystem;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class medicineListFragment extends Fragment {
    String contact;
    private RecyclerView medicineRecycler;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference presRef = db.collection("Prescriptions");
    private medicineListRecyclerAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        HomeScreen myActivity = (HomeScreen)getActivity();
        contact = myActivity.getActivityContact();
       // Toast.makeText(getContext(),contact , Toast.LENGTH_SHORT).show();
        return inflater.inflate(R.layout.medicine_list_frag, container, false);    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        Query query = presRef.whereEqualTo("contact",contact).whereEqualTo("status",true);
        FirestoreRecyclerOptions<patientPrescription> options = new FirestoreRecyclerOptions.Builder<patientPrescription>()
                .setQuery(query,patientPrescription.class)
                .build();

        adapter = new medicineListRecyclerAdapter(options);
        medicineRecycler = (RecyclerView)view.findViewById(R.id.medicine_recycler);
        medicineRecycler.setHasFixedSize(true);
        //medicineRecycler.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false));
        medicineRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        medicineRecycler.setAdapter(adapter);
        adapter.setOnItemClickListener(new medicineListRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onClick(DocumentSnapshot documentSnapshot, int position) {
                String id = documentSnapshot.getId();
                Intent n = new Intent(getActivity(),MedicineDetails.class);
                n.putExtra("MID",id);
                n.putExtra("contact",contact);
                startActivity(n);

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
