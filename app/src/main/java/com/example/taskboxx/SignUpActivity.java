package com.example.taskboxx;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    public String userId;
    public Button signup_button;
    public EditText name;
    public EditText username;
    public EditText email;
    public EditText pwd;
    public EditText age;
    public EditText confirm_pwd;
    public ProgressDialog pd;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        signup_button = (Button) findViewById(R.id.SignUp);
        name = (EditText) findViewById(R.id.input_Name_SignUp);
        username = (EditText) findViewById(R.id.input_Username_SignUp);
        age = (EditText) findViewById(R.id.input_Age_SignUp);
        email = (EditText) findViewById(R.id.input_email_SignUp);
        pwd = (EditText) findViewById(R.id.input_NewPass_SignUp);
        confirm_pwd = (EditText) findViewById(R.id.input_RePass_SignUp);
        pd = new ProgressDialog(this);

    }


    public void SignUp(View view) {


        final String u_name = name.getText().toString();
        final String u_username = username.getText().toString();
        final String u_email = email.getText().toString();
        final String u_pwd = pwd.getText().toString();
        final String u_age = age.getText().toString();
        final String u_c_pwd = confirm_pwd.getText().toString();

        if (TextUtils.isEmpty(u_name) || TextUtils.isEmpty(u_username) || TextUtils.isEmpty(u_email) || TextUtils.isEmpty(u_pwd) || TextUtils.isEmpty(u_c_pwd)) {
            Toast.makeText(this, "Please Enter All the fields to proceed!", Toast.LENGTH_SHORT).show();
            return;
        } else {

            if (u_pwd.equals(u_c_pwd)) {

                pd.setMessage("Registering...");
                pd.show();

                mAuth.createUserWithEmailAndPassword(u_email, u_pwd)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    userId = mAuth.getCurrentUser().getUid();
                                    mDatabase.child("users").child(userId).child("Name").setValue(u_name);
                                    mDatabase.child("users").child(userId).child("Username").setValue(u_username);
                                    mDatabase.child("users").child(userId).child("Age").setValue(u_age);
                                    pd.hide();
                                    Toast.makeText(SignUpActivity.this, "Registered Successfully!", Toast.LENGTH_SHORT).show();
                                    Toast.makeText(SignUpActivity.this, "Please Log in to proceed", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    pd.hide();
                                    Toast.makeText(SignUpActivity.this, "Failed To Register!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            } else {
                Toast.makeText(this, "The Entered Password doesn't Match!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}