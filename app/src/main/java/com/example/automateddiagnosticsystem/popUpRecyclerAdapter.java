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

public class popUpRecyclerAdapter extends FirestoreRecyclerAdapter<userModel,popUpRecyclerAdapter.popUpListViewHolder> {

    private OnItemClickListener mListener;
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public popUpRecyclerAdapter(@NonNull FirestoreRecyclerOptions<userModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull popUpListViewHolder holder, int position, @NonNull userModel model) {
        holder.hospital.setText(model.getHospital());
        holder.doctorName.setText(model.getName());
    }

    @NonNull
    @Override
    public popUpListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.pop_up_recycler_card_view,
                parent, false);
        return new popUpListViewHolder(v);
    }

    public class popUpListViewHolder extends RecyclerView.ViewHolder{

        TextView hospital;
        TextView doctorName;

        public popUpListViewHolder(@NonNull View itemView) {
            super(itemView);
            this.hospital = itemView.findViewById(R.id.pop_up_hospital_name);
            this.doctorName =itemView.findViewById(R.id.pop_up_doctor_name);
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
