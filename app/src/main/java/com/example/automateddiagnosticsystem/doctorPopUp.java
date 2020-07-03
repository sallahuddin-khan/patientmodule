package com.example.automateddiagnosticsystem;

import android.app.Activity;
import android.app.AppComponentFactory;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class doctorPopUp extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference presRef = db.collection("user");
    private popUpRecyclerAdapter adapter;
    String date;                    //Prev
    String time;
    String additionalNote;
    private RecyclerView popUpRec;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.follow_on_doctor_add);
        Bundle extras = getIntent().getExtras();
        date = extras.getString("date");
        time = extras.getString("time");
        additionalNote = extras.getString("additionalNote");
        /*DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int)(width*.8),(int)(height*.6));*/
        setUpRecyclerView();

    }
   public void showResults(View v){

    }
    private void setUpRecyclerView(){
        //Query query = presRef.orderBy("name").startAt(nameOfDoctor).endAt(nameOfDoctor+'\uf8ff');
        Query query = presRef.whereEqualTo("userType","doctor");
        FirestoreRecyclerOptions<userModel> options = new FirestoreRecyclerOptions.Builder<userModel>()
                .setQuery(query,userModel.class)
                .build();
        adapter = new popUpRecyclerAdapter(options);
        popUpRec = (RecyclerView)findViewById(R.id.pop_up_recycler);
        popUpRec.setHasFixedSize(true);
        popUpRec.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        popUpRec.setAdapter(adapter);

        adapter.setOnItemClickListener(new popUpRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onClick(DocumentSnapshot documentSnapshot, int position) {
                userModel mo = documentSnapshot.toObject(userModel.class);

                String email = mo.getEmail();
                String name = mo.getName();
                String hospital = mo.getHospital();
                Toast.makeText(getApplicationContext(),email,Toast.LENGTH_LONG).show();
                Intent n = new Intent(doctorPopUp.this,AddFollowOn.class);
                n.putExtra("State",1);
                n.putExtra("email",email);
                n.putExtra("hospital",hospital);
                n.putExtra("name",name);
                n.putExtra("time",time);
                n.putExtra("date",date);
                n.putExtra("additionalNote",additionalNote);
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
