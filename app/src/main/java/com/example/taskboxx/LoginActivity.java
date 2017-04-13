package com.example.taskboxx;

import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        textView = (TextView) findViewById(R.id.launch_forgotpass);
        textView.setPaintFlags(textView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    }

    public void Login(View view){
        Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();
        Intent dashboardintent = new Intent(this,Dashboard.class);
        startActivity(dashboardintent);
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
