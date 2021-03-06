package com.example.taskboxx;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class Dashboard extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FirebaseAuth mAuth;
    private static Context context;
    TextView email, name;
    String getname,getemail;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        context = this;

        bundle = getIntent().getExtras();
        getname = bundle.getString("Name");
        getemail = bundle.getString("Email");


        mAuth = FirebaseAuth.getInstance();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(1).setChecked(true);
        View header = navigationView.getHeaderView(0);


        name = (TextView) header.findViewById(R.id.LoginName);
        email = (TextView) header.findViewById(R.id.LoginEmail);

        FragmentManager fragmentManager = getFragmentManager();
        Fragment DashboardFragment = new DashboardFragment();
        fragmentManager.beginTransaction().replace(R.id.content_frame,DashboardFragment).commit();

    }

    public static Context getContext1(){
        return context;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            mAuth.getInstance().signOut();
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard, menu);
        name.setText(getname);
        email.setText(getemail);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        FragmentManager fragmentManager = getFragmentManager();

        switch (id) {
            case R.id.nav_my_account: {
                Fragment myAccountFragment = new MyAccountFragment();
                fragmentManager.beginTransaction().replace(R.id.content_frame, myAccountFragment).commit();
                break;
            } case R.id.nav_analytics: {
                Fragment AnalyticsFragment = new AnalyticsFragment();
                fragmentManager.beginTransaction().replace(R.id.content_frame, AnalyticsFragment).commit();
                break;
            } case R.id.nav_dashboard: {
                Fragment DashboardFragment = new DashboardFragment();
                fragmentManager.beginTransaction().replace(R.id.content_frame, DashboardFragment).commit();
                break;
            } case R.id.nav_logout: {
                mAuth.getInstance().signOut();
                finish();
                break;
            } default:{
                Fragment AnalyticsFragment = new AnalyticsFragment();
                fragmentManager.beginTransaction().replace(R.id.content_frame, AnalyticsFragment).commit();
                break;
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
