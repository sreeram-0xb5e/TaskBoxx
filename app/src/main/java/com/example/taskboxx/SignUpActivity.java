package com.example.taskboxx;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

public class SignUpActivity extends AppCompatActivity {

    public Button signup_button;
    public EditText name;
    public EditText username;
    public EditText email;
    public EditText pwd;
    public EditText confirm_pwd;
    public ProgressDialog pd;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();
        signup_button = (Button) findViewById(R.id.SignUp);
        name = (EditText) findViewById(R.id.input_Name_SignUp);
        username = (EditText) findViewById(R.id.input_Username_SignUp);
        email = (EditText)findViewById(R.id.input_email_SignUp);
        pwd =(EditText) findViewById(R.id.input_NewPass_SignUp);
        confirm_pwd = (EditText) findViewById(R.id.input_RePass_SignUp);
        pd = new ProgressDialog(this);

        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartRigister();
            }
        });

    }


    private void StartRigister() {


        String u_name = name.getText().toString();
        String u_username = username.getText().toString();
        String u_email = email.getText().toString();
        String u_pwd = pwd.getText().toString();
        String u_c_pwd = confirm_pwd.getText().toString();

        if (TextUtils.isEmpty(u_name) || TextUtils.isEmpty(u_username) || TextUtils.isEmpty(u_email) || TextUtils.isEmpty(u_pwd) || TextUtils.isEmpty(u_c_pwd)) {
            Toast.makeText(this, "Please Enter All the fields to proceed!", Toast.LENGTH_SHORT).show();
            return;
        }


        
        if (u_pwd.equals(u_c_pwd)) {

            pd.setMessage("Browsy is Registering You!");
            pd.show();


            mAuth.createUserWithEmailAndPassword(u_email, u_pwd)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {


                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {


                            if (task.isSuccessful())
                            {
                                pd.hide();
                                Toast.makeText(SignUpActivity.this,"Registered Successfully!",Toast.LENGTH_SHORT).show();
                            }

                            else {
                                pd.hide();
                                Toast.makeText(SignUpActivity.this,"Failed To Register!",Toast.LENGTH_SHORT).show();
                            }


                        }
                    });



        } else {
            Toast.makeText(this, "The Entered Password doesn't Match!", Toast.LENGTH_SHORT).show();

        }


        //Toast.makeText(this, "Registered!", Toast.LENGTH_SHORT).show();

    }


}
