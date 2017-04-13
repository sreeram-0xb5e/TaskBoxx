package com.example.taskboxx;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {

    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    viewpageradapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        toolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPagerAdapter = new viewpageradapter(getSupportFragmentManager());
        viewPagerAdapter.addFragments(new StudentSignUpFragment(),"Student");
        viewPagerAdapter.addFragments(new FacultySignUpFragment(),"Faculty");
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }

    public void StudentSignUp(View view){
        Toast.makeText(this, "Registered as a Student!", Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "Please Log In to Proceed", Toast.LENGTH_SHORT).show();
        Intent loginintent = new Intent(this,LoginActivity.class);
        startActivity(loginintent);
    }

    public void FacultySignUp(View view){
        Toast.makeText(this, "Registered as a Faculty!", Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "Please Log In to Proceed", Toast.LENGTH_SHORT).show();
        Intent loginintent = new Intent(this,LoginActivity.class);
        startActivity(loginintent);
    }
}
