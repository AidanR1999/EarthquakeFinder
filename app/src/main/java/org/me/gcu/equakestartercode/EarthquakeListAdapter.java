package org.me.gcu.equakestartercode;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class EarthquakeListAdapter extends RecyclerView.Adapter<EarthquakeListAdapter.ViewHolder> {
    ArrayList titles;
    ArrayList longitudes;
    ArrayList latitudes;
    Context context;

    // Constructor for initialization
    public EarthquakeListAdapter(Context context, ArrayList<Earthquake> earthquakes) {
        this.context = context;
        this.titles = getTitles(earthquakes);
        this.longitudes = getLongitudes(earthquakes);
        this.latitudes = getLatitudes(earthquakes);
    }

    private ArrayList<String> getLatitudes(ArrayList<Earthquake> earthquakes) {
        ArrayList<String> latitudes = new ArrayList<>();
        for(int i = 0; i < earthquakes.size(); ++i) {
            latitudes.add(earthquakes.get(i).getLat());
        }
        return latitudes;
    }

    private ArrayList<String> getLongitudes(ArrayList<Earthquake> earthquakes) {
        ArrayList<String> longitudes = new ArrayList<>();
        for(int i = 0; i < earthquakes.size(); ++i) {
            longitudes.add(earthquakes.get(i).getLon());
        }
        return longitudes;
    }

    private ArrayList<String> getTitles(ArrayList<Earthquake> earthquakes) {
        ArrayList<String> titles = new ArrayList<>();
        for(int i = 0; i < earthquakes.size(); ++i) {
            titles.add(earthquakes.get(i).getTitle());
        }
        return titles;
    }

    @NonNull
    @Override
    public EarthquakeListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflating the Layout(Instantiates list_item.xml
        // layout file into View object)
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);

        // Passing view to ViewHolder
        EarthquakeListAdapter.ViewHolder viewHolder = new EarthquakeListAdapter.ViewHolder(view);
        return viewHolder;
    }

    // Binding data to the into specified position
    @Override
    public void onBindViewHolder(@NonNull EarthquakeListAdapter.ViewHolder holder, int position) {
        // TypeCast Object to int type
        holder.titleText.setText((String) titles.get(position));
        holder.latLongText.setText((String) latitudes.get(position) + " " + longitudes.get(position));
    }

    @Override
    public int getItemCount() {
        // Returns number of items
        // currently available in Adapter
        return titles.size();
    }

    // Initializing the Views
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleText;
        TextView latLongText;

        public ViewHolder(View view) {
            super(view);
            titleText = (TextView) view.findViewById(R.id.title);
            latLongText = (TextView) view.findViewById(R.id.longitude);
        }
    }
}