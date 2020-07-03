package com.example.automateddiagnosticsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.concurrent.TimeUnit;

public class SignUp extends AppCompatActivity {

    private EditText InputUserPhoneNumber, InputUserVerificationCode;
    private Button SendVerificationCodeButton, VerifyButton;
    DatabaseReference myDatabaseReference;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    String patientPhone;
    private CollectionReference patientRef = db.collection("patient");
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;
    private FirebaseAuth mAuth;

    private ProgressDialog loadingBar;

    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        myDatabaseReference= FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();


        initializeVariables();



        SendVerificationCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                final String phoneNumber = InputUserPhoneNumber.getText().toString();

                if (TextUtils.isEmpty(phoneNumber))
                {
                    Toast.makeText(SignUp.this, "Please enter your phone number first...", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    loadingBar.setTitle("Phone Verification");
                    loadingBar.setMessage("Please wait, while we are authenticating using your phone...");
                    loadingBar.setCanceledOnTouchOutside(false);
                    loadingBar.show();



                    patientRef
                            .whereEqualTo("contact", phoneNumber).limit(1)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        boolean isEmpty1 = task.getResult().isEmpty();
                                        if(isEmpty1) {
                                            loadingBar.dismiss();
                                            Toast.makeText(getApplicationContext(),"You are not signed up for the use of the system",Toast.LENGTH_LONG).show();
                                        }
                                        else{

                                           // Toast.makeText(getApplicationContext(),"Signed Up",Toast.LENGTH_SHORT).show();
                                            String phoneNumber = InputUserPhoneNumber.getText().toString();
                                            patientPhone= phoneNumber;
                                           // Toast.makeText(getApplicationContext(),patientPhone,Toast.LENGTH_LONG).show();
                                            PhoneAuthProvider.getInstance().verifyPhoneNumber(phoneNumber, 60, TimeUnit.SECONDS, SignUp.this, callbacks);
                                        }


                                    } else {
                                        Log.d("DAAAAAAAAAAAAPPPPPPPPP", "Error getting documents: ", task.getException());
                                    }
                                }
                            });
                }
            }
        });



        VerifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                InputUserPhoneNumber.setVisibility(View.INVISIBLE);
                SendVerificationCodeButton.setVisibility(View.INVISIBLE);


                String verificationCode = InputUserVerificationCode.getText().toString();

                if (TextUtils.isEmpty(verificationCode))
                {
                    Toast.makeText(SignUp.this, "Please write verification code first...", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    loadingBar.setTitle("Verification Code");
                    loadingBar.setMessage("Please wait, while we are verifying verification code...");
                    loadingBar.setCanceledOnTouchOutside(false);
                    loadingBar.show();

                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, verificationCode);
                    signInWithPhoneAuthCredential(credential);

                }
            }
        });


        callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential)
            {
                signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e)
            {
                Toast.makeText(SignUp.this, "Invalid Phone Number, Please enter correct phone number with your country code...", Toast.LENGTH_LONG).show();
                loadingBar.dismiss();

                InputUserPhoneNumber.setVisibility(View.VISIBLE);
                SendVerificationCodeButton.setVisibility(View.VISIBLE);

                InputUserVerificationCode.setVisibility(View.INVISIBLE);
                VerifyButton.setVisibility(View.INVISIBLE);
            }

            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token)
            {
                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;


                Toast.makeText(SignUp.this, "Code has been sent, please check and verify...", Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();

                InputUserPhoneNumber.setVisibility(View.INVISIBLE);
                SendVerificationCodeButton.setVisibility(View.INVISIBLE);

                InputUserVerificationCode.setVisibility(View.VISIBLE);
                VerifyButton.setVisibility(View.VISIBLE);
            }
        };
    }

    private void initializeVariables() {
        InputUserPhoneNumber = (EditText) findViewById(R.id.register_Phone);
        InputUserVerificationCode = (EditText) findViewById(R.id.VerificationCode_forPhone);
        SendVerificationCodeButton = (Button) findViewById(R.id.sendVerificationCode);
        VerifyButton = (Button) findViewById(R.id.VerifyPhoneCode);
        loadingBar = new ProgressDialog(this);

        InputUserPhoneNumber.setVisibility(View.VISIBLE);
        SendVerificationCodeButton.setVisibility(View.VISIBLE);

        InputUserVerificationCode.setVisibility(View.INVISIBLE);
        VerifyButton.setVisibility(View.INVISIBLE);
    }


    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential)
    {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            String currentUserId = mAuth.getCurrentUser().getUid();
                            String deviceToken= FirebaseInstanceId.getInstance().getToken();
                            myDatabaseReference.child("Users").child(currentUserId).child("device_token")
                                    .setValue(deviceToken);
                            loadingBar.dismiss();
                            Toast.makeText(SignUp.this, "Congratulations, you're logged in Successfully.", Toast.LENGTH_SHORT).show();
                            SendUserToMainActivity();
                        }
                        else
                        {
                            String message = task.getException().toString();
                            Toast.makeText(SignUp.this, "Error: " + message, Toast.LENGTH_LONG).show();
                            loadingBar.dismiss();
                        }
                    }
                });
    }




    private void SendUserToMainActivity()
    {
      Intent mainIntent = new Intent(SignUp.this, HomeScreen.class);
        mainIntent.putExtra("Phone",patientPhone);
        startActivity(mainIntent);
        Toast.makeText(SignUp.this, "LOGIN SUCCESSFUL", Toast.LENGTH_LONG).show();
        finish();
    }
}
