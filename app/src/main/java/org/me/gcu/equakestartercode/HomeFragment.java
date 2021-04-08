//Aidan Rooney - S1911669
package org.me.gcu.equakestartercode;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

//displays the list of earthquakes in the last 50 days
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

                Toast.makeText(view.getContext(), "Data Refreshed", Toast.LENGTH_SHORT).show();

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
