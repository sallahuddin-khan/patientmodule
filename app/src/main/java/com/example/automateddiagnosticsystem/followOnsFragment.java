package com.example.automateddiagnosticsystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class followOnsFragment extends Fragment {
    String contact;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference presRef = db.collection("FollowOns");
    private followOnAdapter adapter;
    private RecyclerView followOnRecycler;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        HomeScreen myActivity = (HomeScreen)getActivity();
        contact = myActivity.getActivityContact();
        View v = inflater.inflate(R.layout.follow_ons_fragment, container, false);
        ImageButton im = v.findViewById(R.id.make_new_button);
        im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(getActivity(), AddFollowOn.class);
                mainIntent.putExtra("State",0);
                startActivity(mainIntent);
            }
        });

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Query query = presRef.whereEqualTo("contact",contact);
        FirestoreRecyclerOptions<PatientsFollowOn> options = new FirestoreRecyclerOptions.Builder<PatientsFollowOn>()
                .setQuery(query,PatientsFollowOn.class)
                .build();

        adapter = new followOnAdapter(options);
        followOnRecycler = (RecyclerView)view.findViewById(R.id.follow_on_recycler);
        followOnRecycler.setHasFixedSize(true);
        followOnRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        followOnRecycler.setAdapter(adapter);
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                adapter.deleteItem(viewHolder.getAdapterPosition());
            }
        }).attachToRecyclerView(followOnRecycler);
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
