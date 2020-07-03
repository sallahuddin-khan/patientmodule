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

public class dailyDosageAdapter extends FirestoreRecyclerAdapter<patientPrescription,dailyDosageAdapter.dailyMedicineListViewHolder> {
    private dailyDosageAdapter.OnItemClickListener mListener;

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public dailyDosageAdapter(@NonNull FirestoreRecyclerOptions<patientPrescription> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull dailyMedicineListViewHolder holder, int position, @NonNull patientPrescription model) {
        holder.medicineName.setText(model.getMedicine());
        holder.medicineDosage.setText(model.getDosage());
    }

    @NonNull
    @Override
    public dailyMedicineListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.daily_medicine_view,
                parent, false);
        return new dailyMedicineListViewHolder(v);
    }


    public class dailyMedicineListViewHolder extends RecyclerView.ViewHolder{
        TextView medicineName;
        TextView medicineDosage;
        public dailyMedicineListViewHolder(@NonNull View itemView) {
            super(itemView);
            this.medicineName = itemView.findViewById(R.id.daily_medicine_name);
            this.medicineDosage=itemView.findViewById(R.id.daily_medicine_dosage);
            itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION && mListener != null) {
                            mListener.onClick(getSnapshots().getSnapshot(position), position);
                        }
                    }

                }
            });
        }
    }
    public interface OnItemClickListener {
        void onClick(DocumentSnapshot documentSnapshot, int position);
    }

    public void setOnItemClickListener(dailyDosageAdapter.OnItemClickListener listener) {
        mListener = listener;
    }
}
