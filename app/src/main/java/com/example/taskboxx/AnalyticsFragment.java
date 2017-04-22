package com.example.taskboxx;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import static android.content.ContentValues.TAG;

public class AnalyticsFragment extends Fragment {


    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private ArrayList<Float> yData = new ArrayList<>();
    private ArrayList<String> xData = new ArrayList<>();
    PieChart pieChart;
    ProgressDialog pd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.analytics_fragment,container,false);
        getActivity().setTitle("Analytics");

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        pd = new ProgressDialog(Dashboard.getContext1());


        pieChart = (PieChart) rootView.findViewById(R.id.idPieChart);
        pd.setMessage("Loading...");
        pd.show();

        pieChart.setDescription(null);
        pieChart.setRotationEnabled(true);

        pieChart.setHoleRadius(60f);
        pieChart.setUsePercentValues(true);
        pieChart.setTransparentCircleAlpha(175);
        pieChart.setTransparentCircleRadius(63f);
        pieChart.setCenterText("Browsing Content");
        pieChart.setCenterTextSize(15);

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                HashMap<String,Float> values = (HashMap)dataSnapshot.child("users").child(mAuth.getCurrentUser().getUid()).child("Data").getValue();
                Log.d(TAG, "Hashmap values: "+values);
                Set mapSet = (Set) values.entrySet();
                Iterator mapIterator = mapSet.iterator();
                while(mapIterator.hasNext())
                {
                    Map.Entry mapEntry = (Map.Entry) mapIterator.next();
                    xData.add(mapEntry.getKey().toString());
                    yData.add(Double.valueOf((Double)mapEntry.getValue()).floatValue());
                }
                Log.d(TAG, "String Tags: "+xData);
                Log.d(TAG, "String Values: "+yData);
                addDataSet();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(getActivity(), "Cannot Retrieve Data from Database", Toast.LENGTH_SHORT).show();
            }
        });

        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {

                int pos1 = e.toString().indexOf("(sum): ");
                String percent = e.toString().substring(pos1 + 7);

                for(int i = 0; i < yData.size(); i++){
                    if(yData.get(i) == Float.parseFloat(percent)){
                        pos1 = i;
                        break;
                    }
                }
                String type = xData.get(pos1);
                Toast.makeText(getActivity(), "You browse for " + type + "\n" + "for " + percent + "% of the time", Toast.LENGTH_LONG).show();
            }


            @Override
            public void onNothingSelected() {

            }
        });

        return rootView;
    }

    private void addDataSet() {

        final ArrayList<PieEntry> yEntrys = new ArrayList<>();

        for(int i = 0; i < yData.size(); i++){
            yEntrys.add(new PieEntry(yData.get(i) , xData.get(i)));
        }

        //create the data set
        PieDataSet pieDataSet = new PieDataSet(yEntrys, "Your Browsing Content");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(0);

        //add colors to dataset
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.BLUE);
        colors.add(Color.RED);
        colors.add(Color.GREEN);
        colors.add(Color.DKGRAY);
        colors.add(Color.BLACK);
        colors.add(Color.MAGENTA);

        pieDataSet.setColors(colors);

        pd.dismiss();

        //create pie data object
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.animateXY(1500,1500, Easing.EasingOption.EaseInOutSine, Easing.EasingOption.EaseInOutSine);
    }
}
