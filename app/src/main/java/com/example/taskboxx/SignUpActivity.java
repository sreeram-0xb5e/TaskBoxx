package com.example.taskboxx;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }

    public void SignUp(View view){
        Toast.makeText(this, "Registered!", Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "Please Log In to Proceed", Toast.LENGTH_SHORT).show();
        Intent loginintent = new Intent(this,LoginActivity.class);
        startActivity(loginintent);
    }
}
