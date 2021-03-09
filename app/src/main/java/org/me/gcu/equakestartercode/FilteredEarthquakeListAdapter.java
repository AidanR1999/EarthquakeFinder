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

public class FilteredEarthquakeListAdapter extends RecyclerView.Adapter<FilteredEarthquakeListAdapter.ViewHolder> {
    ArrayList<Earthquake> earthquakes;
    ArrayList<Earthquake> filtered;
    Context context;
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

    private ArrayList<Earthquake> assignFiltered() {
        ArrayList<Earthquake> filtered = new ArrayList<>();

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
        // TypeCast Object to int type
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
        TextView titleText;
        TextView magnitude;
        TextView date;
        TextView special;

        public ViewHolder(View view) {
            super(view);
            titleText = (TextView) view.findViewById(R.id.title);
            magnitude = (TextView) view.findViewById(R.id.magnitude);
            date = (TextView) view.findViewById(R.id.date);
            special = (TextView) view.findViewById(R.id.special);
        }
    }

    private Earthquake findMostNorthern() {
        Earthquake nothern = earthquakes.get(0);
        for(int i = 0; i < earthquakes.size(); ++i) {
            if(earthquakes.get(i).getLat() > nothern.getLat()) {
                nothern = earthquakes.get(i);
            }
        }
        return nothern;
    }
    private Earthquake findMostSouthern() {
        Earthquake southern = earthquakes.get(0);
        for(int i = 0; i < earthquakes.size(); ++i) {
            if(earthquakes.get(i).getLat() < southern.getLat()) {
                southern = earthquakes.get(i);
            }
        }
        return southern;
    }
    private Earthquake findMostWestern() {
        Earthquake western = earthquakes.get(0);
        for(int i = 0; i < earthquakes.size(); ++i) {
            if(earthquakes.get(i).getLon() < western.getLon()) {
                western = earthquakes.get(i);
            }
        }
        return western;
    }
    private Earthquake findMostEastern() {
        Earthquake eastern = earthquakes.get(0);
        for(int i = 0; i < earthquakes.size(); ++i) {
            if(earthquakes.get(i).getLon() > eastern.getLon()) {
                eastern = earthquakes.get(i);
            }
        }
        return eastern;
    }
    private Earthquake findLargestMagnitude() {
        Earthquake largest = earthquakes.get(0);
        for(int i = 0; i < earthquakes.size(); ++i) {
            if(earthquakes.get(i).getMagnitude() > largest.getMagnitude()) {
                largest = earthquakes.get(i);
            }
        }
        return largest;
    }
    private Earthquake findDeepest() {
        Earthquake deepest = earthquakes.get(0);
        for(int i = 0; i < earthquakes.size(); ++i) {
            if(earthquakes.get(i).getDepth() > deepest.getDepth()) {
                deepest = earthquakes.get(i);
            }
        }
        return deepest;
    }
    private Earthquake findShallowest() {
        Earthquake shallowest = earthquakes.get(0);
        for(int i = 0; i < earthquakes.size(); ++i) {
            if(earthquakes.get(i).getDepth() < shallowest.getDepth()) {
                shallowest = earthquakes.get(i);
            }
        }
        return shallowest;
    }


}
