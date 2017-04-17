package com.example.taskboxx;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    TextView textView;
    private EditText Email;
    private EditText pass;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    public ProgressDialog pd;
    public static final String TAG = "TAG";

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Email = (EditText) findViewById(R.id.input_uname_login);
        pass = (EditText) findViewById(R.id.input_password_login);
        textView = (TextView) findViewById(R.id.launch_forgotpass);
        textView.setPaintFlags(textView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        pd = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };
    }

    public void Login(View view) {
        String email = Email.getText().toString();
        String password = pass.getText().toString();
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter the Email and Password!", Toast.LENGTH_SHORT).show();
        } else {
            pd.setMessage("Logging in. Please Wait...");
            pd.show();
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());
                            if (!task.isSuccessful()) {
                                pd.dismiss();
                                Log.w(TAG, "signInWithEmail:failed", task.getException());
                                Toast.makeText(LoginActivity.this, task.getException().getMessage().toString(),
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                pd.dismiss();
                                Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                Intent dashboardintent = new Intent(LoginActivity.this, Dashboard.class);
                                startActivity(dashboardintent);
                            }
                        }
                    });
        }
    }

    public void gotoSignUp(View view){
        Toast.makeText(this, "Great Idea!", Toast.LENGTH_SHORT).show();
        Intent signupintent = new Intent(this,SignUpActivity.class);
        startActivity(signupintent);
    }

    public void forgotPass(View view){
        Toast.makeText(this, "No problem!", Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "But the feature isn't implemented yet", Toast.LENGTH_SHORT).show();
    }
}
