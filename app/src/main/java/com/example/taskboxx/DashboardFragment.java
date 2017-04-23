package com.example.taskboxx;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import static android.content.ContentValues.TAG;

public class DashboardFragment extends Fragment {

    ArrayList<String> bookmarks;
    static HashMap<String,String> bookmarkdetails;
    ListView bookmarksView;
    DatabaseReference mDatabase;
    FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dashboard_fragment,container,false);
        getActivity().setTitle("Dashboard");

        bookmarksView = (ListView) rootView.findViewById(R.id.bookmarks);
        bookmarks = new ArrayList<>();
        bookmarkdetails = new LinkedHashMap<>();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                bookmarkdetails = (HashMap)dataSnapshot.child("users").child(mAuth.getCurrentUser().getUid()).child("Bookmarks").getValue();
                Log.d(TAG, "Bookmarks: "+bookmarkdetails);
                Set set = (Set) bookmarkdetails.entrySet();
                Iterator iterator = set.iterator();
                while(iterator.hasNext()){
                    Map.Entry mapEntry = (Map.Entry) iterator.next();
                    bookmarks.add(mapEntry.getKey().toString());
                }
                setupListView();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


        bookmarksView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String selectedbookmark = bookmarks.get(position);
                String URL = "http://"+bookmarkdetails.get(selectedbookmark);
                Toast.makeText(getActivity().getApplicationContext(), "Bookmark selected: "+selectedbookmark, Toast.LENGTH_SHORT).show();
                Uri uri = Uri.parse(URL);
                Intent BrowserIntent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(BrowserIntent);
            }
        });

        return rootView;
    }

    public void setupListView(){

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,bookmarks);

        bookmarksView.setAdapter(arrayAdapter);

    }
}
