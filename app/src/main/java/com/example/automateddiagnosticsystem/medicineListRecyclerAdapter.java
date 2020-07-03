package com.example.automateddiagnosticsystem;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class medicineListRecyclerAdapter extends FirestoreRecyclerAdapter<patientPrescription,medicineListRecyclerAdapter.medicineListViewHolder>{
    private OnItemClickListener mListener;

    public medicineListRecyclerAdapter(@NonNull FirestoreRecyclerOptions<patientPrescription> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull medicineListViewHolder holder, int position, @NonNull patientPrescription model) {
        holder.medicineName.setText(model.getMedicine());
        holder.medicineDoctor.setText(model.getDoctor());
        holder.medicineDosage.setText(model.getDosage());
        String timings;
        if (model.isMorning())
            timings = "Morning";
        else
            timings = "";
        holder.medicineDescriptionMorning.setText(timings);
        if (model.isAfternoon())
            timings = "Afternoon";
        else
            timings = "";
        holder.medicineDescriptionAfternoon.setText(timings);
        if (model.isEvening())
            timings = "Evening";
        else
            timings = "";
        holder.medicineDescriptionEvening.setText(timings);
    }

    @NonNull
    @Override
    public medicineListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.medicine_object_view,
                parent, false);
        return new medicineListViewHolder(v);
    }

    public class medicineListViewHolder extends RecyclerView.ViewHolder{
        TextView medicineName;
        TextView medicineDoctor;
        TextView medicineDescriptionMorning;
        TextView medicineDescriptionAfternoon;
        TextView medicineDescriptionEvening;
        TextView medicineDosage;
        public medicineListViewHolder(@NonNull View itemView) {
            super(itemView);
            this.medicineName = itemView.findViewById(R.id.text_view_medicine_name);
            this.medicineDoctor = itemView.findViewById(R.id.text_view_doctor);
            this.medicineDescriptionMorning=itemView.findViewById(R.id.text_view_description);
            this.medicineDescriptionAfternoon=itemView.findViewById(R.id.text_view_description1);
            this.medicineDescriptionEvening=itemView.findViewById(R.id.text_view_description2);
            this.medicineDosage=itemView.findViewById(R.id.text_view_description3);
            itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION && mListener != null) {
                            mListener.onClick(getSnapshots().getSnapshot(position),position);
                        }
                    }

                }
            });
        }
    }
    public interface OnItemClickListener {
        void onClick(DocumentSnapshot documentSnapshot, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }
}
