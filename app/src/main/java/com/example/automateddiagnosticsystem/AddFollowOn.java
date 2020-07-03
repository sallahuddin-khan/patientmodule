package com.example.automateddiagnosticsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class AddFollowOn extends AppCompatActivity {
    TextView dateView;
    TextView hospitalNameView;
    TextView doctorNameView;
    TextView timeView;
    TextView additionalNoteView;
    String date;
    String hospitalName;
    String doctorName;
    String time;
    String additionalNote;
    String doctorEmail;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private TimePickerDialog.OnTimeSetListener mTimeSetListener;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private CollectionReference patientRef = db.collection("FollowOns");
    public static final String DATE_FORMAT_1 = "dd-MM-yyyy";

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_follow_on);
        Bundle extras = getIntent().getExtras();
        int i = extras.getInt("State");
        dateView =(TextView)findViewById(R.id.date_follow_on);
        hospitalNameView=(TextView)findViewById(R.id.hospital_follow_on);
        doctorNameView=(TextView)findViewById(R.id.doctor_follow_on);
        timeView =(TextView)findViewById(R.id.time_follow_on);
        additionalNoteView=(TextView)findViewById(R.id.note_follow_on);

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(" SSSSSS ", "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                date = day +"-" +month+ "-" + year;
                dateView.setText(date);
            }
        };
        mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                time = i+":"+i1;
                timeView.setText(time);
            }
        };
        hospitalName=hospitalNameView.getText().toString();
        doctorName=doctorNameView.getText().toString();
        if(i==0){
        }
        else if(i==1){
            String doctorN =  extras.getString("name");
            String doctorHospital =  extras.getString("hospital");
            date = extras.getString("date");
            time = extras.getString("time");
            additionalNote = extras.getString("additionalNote");
            doctorEmail =  extras.getString("email");  //Class Variable
            doctorNameView.setText(doctorN);
            hospitalNameView.setText(doctorHospital);
            dateView.setText(date);
            timeView.setText(time);
            additionalNoteView.setText(additionalNote);
        }

    }



    public void setDate(View v){
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog = new DatePickerDialog(
                AddFollowOn.this,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                mDateSetListener,
                year,month,day);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

public void setTime(View v){
    Calendar c = Calendar.getInstance();
    int hour = c.get(Calendar.HOUR_OF_DAY);
    int minute = c.get(Calendar.MINUTE);
    TimePickerDialog dialog = new TimePickerDialog(
            AddFollowOn.this,
            android.R.style.Theme_Holo_Light_Dialog_MinWidth,
            mTimeSetListener,
            hour,
            minute,
            true
    );
    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    dialog.show();
}
public void SaveFollowOn(View v){
        time = timeView.getText().toString();
        hospitalName=hospitalNameView.getText().toString();
        doctorName=doctorNameView.getText().toString();
        date=dateView.getText().toString();
        additionalNote = additionalNoteView.getText().toString();
    FirebaseUser user = firebaseAuth.getCurrentUser();

    if(time.isEmpty() || date.isEmpty()){
        Toast.makeText(getApplicationContext(),"Field(s) Empty",Toast.LENGTH_SHORT).show();
    }
    else {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_1);
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date today = Calendar.getInstance().getTime();
//Contact and reminder needs to be sorted
        String currentDate = dateFormat.format(today);
        String[] separatedCurrentDate = currentDate.split("-");
        String[] separatedReqDate=date.split("-");

        boolean dateFlag = false;
        if(Integer.parseInt(separatedCurrentDate[2])<Integer.parseInt(separatedReqDate[2]))
            dateFlag=true;
        else if (Integer.parseInt(separatedCurrentDate[2])==Integer.parseInt(separatedReqDate[2]) && Integer.parseInt(separatedCurrentDate[1])<Integer.parseInt(separatedReqDate[1]))
            dateFlag=true;
        else if (Integer.parseInt(separatedCurrentDate[2])==Integer.parseInt(separatedReqDate[2]) && Integer.parseInt(separatedCurrentDate[1])==Integer.parseInt(separatedReqDate[1]) &&  Integer.parseInt(separatedCurrentDate[0])<Integer.parseInt(separatedReqDate[0]))
            dateFlag=true;
        else
            dateFlag=false;


        if(dateFlag) {
            if (user != null) {
                String number = user.getPhoneNumber();
                PatientsFollowOn pt = new PatientsFollowOn(date, dateFormat.format(today), doctorName, true, number, additionalNote, hospitalName, time, doctorEmail, true);
                patientRef.add(pt).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getApplicationContext(), "Follow On added", Toast.LENGTH_SHORT).show();
                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(), "DATA NOT ADDED", Toast.LENGTH_SHORT).show();
                            }
                        });


            } else {
                Toast.makeText(getApplicationContext(), "User Not Logged In", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(getApplicationContext(),"The date should be more than one day ahead",Toast.LENGTH_SHORT).show();
        }
//-----------

        finish();
    }
}

public void addDoctor(View v){

        Intent pop = new Intent(AddFollowOn.this,doctorPopUp.class);
        time = timeView.getText().toString();
        date=dateView.getText().toString();
        additionalNote = additionalNoteView.getText().toString();
        pop.putExtra("time",time);
        pop.putExtra("date",date);
        pop.putExtra("additionalNote",additionalNote);
        startActivity(pop);

}

public void returnBack(View v){
        finish();
}
}
