//Aidan Rooney - S1911669
package org.me.gcu.equakestartercode;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;

//adapter for displaying filtered earthquake information
public class FilteredEarthquakeListAdapter extends RecyclerView.Adapter<FilteredEarthquakeListAdapter.ViewHolder> {
    //declare variables
    ArrayList<Earthquake> earthquakes;
    ArrayList<Earthquake> filtered;
    Context context;

    //declare headings for filtered list
    String specials[] = {
            "Most Northerly Earthquake",
            "Most Southerly Earthquake",
            "Most Westerly Earthquake",
            "Most Easterly Earthquake",
            "Largest Magnitude Earthquake",
            "Deepest Earthquake",
            "Shallowest Earthquake"
    };


    // Constructor for initialization
    public FilteredEarthquakeListAdapter(Context context, ArrayList<Earthquake> earthquakes) {
        this.context = context;
        this.earthquakes = earthquakes;
        this.filtered = assignFiltered();
    }

    //calculates the earthquake that
    private ArrayList<Earthquake> assignFiltered() {
        //declare filtered
        ArrayList<Earthquake> filtered = new ArrayList<>();

        //calculate earthquake and add them to filtered list
        filtered.add(findMostNorthern());
        filtered.add(findMostSouthern());
        filtered.add(findMostWestern());
        filtered.add(findMostEastern());
        filtered.add(findLargestMagnitude());
        filtered.add(findDeepest());
        filtered.add(findShallowest());

        return filtered;
    }

    @NonNull
    @Override
    public FilteredEarthquakeListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflating the Layout(Instantiates list_item.xml
        // layout file into View object)
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.filtered_list_item, parent, false);

        // Passing view to ViewHolder
        FilteredEarthquakeListAdapter.ViewHolder viewHolder = new FilteredEarthquakeListAdapter.ViewHolder(view);
        return viewHolder;
    }

    // Binding data to the into specified position
    @Override
    public void onBindViewHolder(@NonNull FilteredEarthquakeListAdapter.ViewHolder holder, int position) {
        //display earthquake info
        holder.titleText.setText((String) filtered.get(position).getLocation());
        holder.date.setText("Date: " +  filtered.get(position).getStringDate());
        holder.special.setText(specials[position]);

        //magnitude colour
        if(filtered.get(position).getMagnitude() > 2) {
            holder.magnitude.setTextColor(Color.RED);
        } else if(filtered.get(position).getMagnitude() > 1) {
            holder.magnitude.setTextColor(Color.parseColor("#ffa500"));
        } else {
            holder.magnitude.setTextColor(Color.parseColor("#ffd700"));
        }

        //show magnitude
        holder.magnitude.setText("Magnitude: " + filtered.get(position).getMagnitude());
    }

    @Override
    public int getItemCount() {
        // Returns number of items
        // currently available in Adapter
        return filtered.size();
    }

    // Initializing the Views
    public class ViewHolder extends RecyclerView.ViewHolder {
        //declare text views
        TextView titleText;
        TextView magnitude;
        TextView date;
        TextView special;

        public ViewHolder(View view) {
            super(view);

            //init text views
            titleText = (TextView) view.findViewById(R.id.title);
            magnitude = (TextView) view.findViewById(R.id.magnitude);
            date = (TextView) view.findViewById(R.id.date);
            special = (TextView) view.findViewById(R.id.special);
        }
    }

    //finds most northern earthquake
    private Earthquake findMostNorthern() {
        //store first element as most northern
        Earthquake northern = earthquakes.get(0);

        //for every earthquake, compare latitudes
        for(int i = 0; i < earthquakes.size(); ++i) {
            //if latitude is larger, set northern as current earthquake
            if(earthquakes.get(i).getLat() > northern.getLat()) {
                northern = earthquakes.get(i);
            }
        }
        return northern;
    }

    //finds most southern earthquake
    private Earthquake findMostSouthern() {
        //store first element as most southern
        Earthquake southern = earthquakes.get(0);

        //for every earthquake, compare latitudes
        for(int i = 0; i < earthquakes.size(); ++i) {
            //if latitude is smaller, set southern as current earthquake
            if(earthquakes.get(i).getLat() < southern.getLat()) {
                southern = earthquakes.get(i);
            }
        }
        return southern;
    }

    //finds most western earthquakes
    private Earthquake findMostWestern() {
        //store first element as most western
        Earthquake western = earthquakes.get(0);

        //for every earthquake, compare longitudes
        for(int i = 0; i < earthquakes.size(); ++i) {
            //if longitude is smaller, set western as current earthquake
            if(earthquakes.get(i).getLon() < western.getLon()) {
                western = earthquakes.get(i);
            }
        }
        return western;
    }

    //finds most eastern earthquakes
    private Earthquake findMostEastern() {
        //store first element as most eastern
        Earthquake eastern = earthquakes.get(0);

        //for every earthquake, compare longitudes
        for(int i = 0; i < earthquakes.size(); ++i) {
            //if longitude is larger, set eastern as current earthquake
            if(earthquakes.get(i).getLon() > eastern.getLon()) {
                eastern = earthquakes.get(i);
            }
        }
        return eastern;
    }

    //find earthquake with largest magnitude
    private Earthquake findLargestMagnitude() {
        //store first element as largest
        Earthquake largest = earthquakes.get(0);

        //for every earthquake, compare magnitudes
        for(int i = 0; i < earthquakes.size(); ++i) {
            //if magnitude is larger, set largest as current earthquake
            if(earthquakes.get(i).getMagnitude() > largest.getMagnitude()) {
                largest = earthquakes.get(i);
            }
        }
        return largest;
    }

    //find deepest earthquake
    private Earthquake findDeepest() {
        //store first element as deepest
        Earthquake deepest = earthquakes.get(0);

        //for every earthquake, compare depth
        for(int i = 0; i < earthquakes.size(); ++i) {
            //if depth is larger, set deepest as current earthquake
            if(earthquakes.get(i).getDepth() > deepest.getDepth()) {
                deepest = earthquakes.get(i);
            }
        }
        return deepest;
    }

    //find shallowest earthquake
    private Earthquake findShallowest() {
        //store first element as shallowest
        Earthquake shallowest = earthquakes.get(0);

        //for every earthquake, compare depth
        for(int i = 0; i < earthquakes.size(); ++i) {
            //if depth is smaller, set shallowest as current earthquake
            if(earthquakes.get(i).getDepth() < shallowest.getDepth()) {
                shallowest = earthquakes.get(i);
            }
        }
        return shallowest;
    }
}
