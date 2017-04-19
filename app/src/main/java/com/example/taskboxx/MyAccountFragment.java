package com.example.taskboxx;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MyAccountFragment extends Fragment {

    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    ProgressDialog pd;
    TextView nameView,unameView,ageView,emailView;
    String id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.my_account_fragment,container,false);
        getActivity().setTitle("My Account");

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        pd = new ProgressDialog(rootView.getContext());

        id = mAuth.getCurrentUser().getUid();
        nameView = (TextView) rootView.findViewById(R.id.Name_text);
        unameView = (TextView) rootView.findViewById(R.id.UserName_text);
        ageView = (TextView) rootView.findViewById(R.id.Age_text);
        emailView = (TextView) rootView.findViewById(R.id.Email_text);

        pd.setMessage("Loading...");
        pd.show();

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = (String) dataSnapshot.child("users").child(id).child("Name").getValue();
                String uname = (String) dataSnapshot.child("users").child(id).child("Username").getValue();
                String age = (String) dataSnapshot.child("users").child(id).child("Age").getValue();
                String email = (String) dataSnapshot.child("users").child(id).child("Email").getValue();
                setDetails(name,uname,age,email);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Cannot Retrieve Data from Database", Toast.LENGTH_SHORT).show();

            }
        });



        return rootView;
    }

    private void setDetails(String nameV,String unameV,String ageV,String emailV) {

        nameView.setText(nameV);
        unameView.setText(unameV);
        ageView.setText(ageV);
        emailView.setText(emailV);

        pd.dismiss();
    }

}
