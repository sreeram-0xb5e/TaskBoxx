package com.example.taskboxx;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class DashboardFragment extends Fragment {

    ArrayList<String> bookmarks;
    ListView bookmarksView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dashboard_fragment,container,false);
        getActivity().setTitle("Dashboard");

        bookmarksView = (ListView) rootView.findViewById(R.id.bookmarks);
        bookmarks = new ArrayList<>();
        getBookmarks();

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,bookmarks);

        bookmarksView.setAdapter(arrayAdapter);

        bookmarksView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String selectedbookmark = bookmarks.get(position);
                Toast.makeText(getActivity().getApplicationContext(), "Bookmark selected: "+selectedbookmark, Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }

    public void getBookmarks(){
        bookmarks.add("Google");
        bookmarks.add("Facebook");
        bookmarks.add("Youtube");
        bookmarks.add("Gmail");
        bookmarks.add("Tinder");
        bookmarks.add("Twitch");
    }


}
