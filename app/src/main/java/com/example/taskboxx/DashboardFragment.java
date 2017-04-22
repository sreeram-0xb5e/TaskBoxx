package com.example.taskboxx;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class DashboardFragment extends Fragment {

    ArrayList<String> bookmarks;
    LinkedHashMap<String,String> bookmarkdetails;
    ListView bookmarksView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dashboard_fragment,container,false);
        getActivity().setTitle("Dashboard");

        bookmarksView = (ListView) rootView.findViewById(R.id.bookmarks);
        bookmarks = new ArrayList<>();
        bookmarkdetails = new LinkedHashMap<>();
        getBookmarks();

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,bookmarks);

        bookmarksView.setAdapter(arrayAdapter);

        bookmarksView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String selectedbookmark = bookmarks.get(position);
                String URL = bookmarkdetails.get(selectedbookmark);
                Toast.makeText(getActivity().getApplicationContext(), "Bookmark selected: "+selectedbookmark, Toast.LENGTH_SHORT).show();
                Uri uri = Uri.parse(URL);
                Intent BrowserIntent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(BrowserIntent);
            }
        });

        return rootView;
    }

    public void getBookmarks(){
        bookmarkdetails.put("Google","http://www.google.co.in");
        bookmarkdetails.put("Facebook","http://www.facebook.com");
        bookmarkdetails.put("Youtube","http://www.youtube.com");
        bookmarkdetails.put("GitHub","http://www.github.com");

        Set set = (Set) bookmarkdetails.entrySet();
        Iterator iterator = set.iterator();
        while(iterator.hasNext()){
            Map.Entry mapEntry = (Map.Entry) iterator.next();
            bookmarks.add(mapEntry.getKey().toString());
        }
    }


}
