package com.example.automateddiagnosticsystem;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
public class followOnAdapter extends FirestoreRecyclerAdapter<PatientsFollowOn,followOnAdapter.followOnListViewHolder>{
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public followOnAdapter(@NonNull FirestoreRecyclerOptions<PatientsFollowOn> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull followOnListViewHolder holder, int position, @NonNull PatientsFollowOn model) {
        holder.dateFollow.setText("Date: " + model.getFollowondate());
        holder.description.setText("Note: " +model.getDescription());
        holder.hospital.setText("Hospital: " +model.getHospital());
        holder.time.setText("Time: " +model.getTime());
        holder.doctorName.setText("Doctor: " +model.getDoctorname());
        if(model.isRequested()){
            holder.requestedStatus.setText("The request is pending. Waiting for approval");
        }
        else{
            holder.requestedStatus.setText("The request is accepted by the doctor");
        }

    }

    @NonNull
    @Override
    public followOnListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.follow_on_object_view,
                parent, false);
        return new followOnListViewHolder(v);
    }
    //private medicineListRecyclerAdapter.OnItemClickListener mListener;
    public void deleteItem(int position) {
        getSnapshots().getSnapshot(position).getReference().delete();

    }

    public class followOnListViewHolder extends RecyclerView.ViewHolder{
        TextView dateFollow;
        TextView hospital;
        TextView doctorName;
        TextView description;
        TextView time;
        TextView requestedStatus;
        public followOnListViewHolder(@NonNull View itemView) {
            super(itemView);
            this.dateFollow = itemView.findViewById(R.id.follow_on_date);
            this.hospital = itemView.findViewById(R.id.follow_on_hospital_name);
            this.doctorName =itemView.findViewById(R.id.follow_on_doctor);
            this.description = itemView.findViewById(R.id.follow_on_description);
            this.time = itemView.findViewById(R.id.follow_on_time);
            this.requestedStatus = itemView.findViewById(R.id.follow_on_requested);
        }
    }
}
