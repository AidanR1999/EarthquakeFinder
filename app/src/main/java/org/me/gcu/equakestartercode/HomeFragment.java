//Aidan Rooney - S1911669
package org.me.gcu.equakestartercode;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.io.StringReader;
import java.lang.reflect.Array;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import org.me.gcu.equakestartercode.R;

//displays the list of earthquakes in the last 100 days
public class HomeFragment extends Fragment {

    //declare variables
    private RecyclerView recyclerView;
    private ArrayList<Earthquake> earthquakes;
    private SwipeRefreshLayout swipeRefreshLayout;
    private View view;

    //create the fragment view
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //inflate view
        view = inflater.inflate(R.layout.home_fragment_view, container, false);

        //configure recycler view
        recyclerView = (RecyclerView) view.findViewById(R.id.lstEarthquake);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        //get earthquake data from main activity
        Bundle args = requireArguments();
        this.earthquakes = (ArrayList<Earthquake>) args.getSerializable("earthquakes");

        //add earthquakes to recycler view
        addEarthquakesToList(earthquakes);

        //configure swipe refresh to refresh earthquake data
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //reset refreshing
                swipeRefreshLayout.setRefreshing(false);

                //call startProgress to retrieve updated data
                ((MainActivity) getActivity()).startProgress();
            }
        });
        return view;
    }

    //adds earthquake info to recycler view
    public void addEarthquakesToList(ArrayList<Earthquake> earthquakes) {
        //display earthquakes
        EarthquakeListAdapter adapter = new EarthquakeListAdapter(view.getContext(), earthquakes);
        recyclerView.setAdapter(adapter);
    }

}
