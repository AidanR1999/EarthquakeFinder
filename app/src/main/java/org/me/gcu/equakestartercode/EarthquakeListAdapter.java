package org.me.gcu.equakestartercode;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;

import java.io.Serializable;
import java.util.ArrayList;

public class EarthquakeListAdapter extends RecyclerView.Adapter<EarthquakeListAdapter.ViewHolder> {
    ArrayList<Earthquake> earthquakes;
    Context context;

    // Constructor for initialization
    public EarthquakeListAdapter(Context context, ArrayList<Earthquake> earthquakes) {
        this.context = context;
        this.earthquakes = earthquakes;
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
        holder.titleText.setText((String) earthquakes.get(position).getLocation());
        holder.date.setText("Date: " +  earthquakes.get(position).getStringDate());

        //magnitude colour
        if(earthquakes.get(position).getMagnitude() > 2) {
            holder.magnitude.setTextColor(Color.RED);
        } else if(earthquakes.get(position).getMagnitude() > 1) {
            holder.magnitude.setTextColor(Color.parseColor("#ffa500"));
        } else {
            holder.magnitude.setTextColor(Color.parseColor("#ffd700"));
        }
        holder.magnitude.setText("Magnitude: " + earthquakes.get(position).getMagnitude());
    }

    @Override
    public int getItemCount() {
        // Returns number of items
        // currently available in Adapter
        return earthquakes.size();
    }

    // Initializing the Views
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleText;
        TextView latLongText;
        TextView magnitude;
        TextView date;

        public ViewHolder(View view) {
            super(view);
            titleText = (TextView) view.findViewById(R.id.title);
            magnitude = (TextView) view.findViewById(R.id.magnitude);
            date = (TextView) view.findViewById(R.id.date);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context ctx = v.getContext();
                    int index = getAdapterPosition();

                    Intent intent = new Intent(ctx, MapViewer.class);
                    Bundle args = new Bundle();
                    args.putSerializable("ARRAYLIST", (Serializable) earthquakes);
                    intent.putExtra("BUNDLE", args);
                    intent.putExtra("index", index);
                    ctx.startActivity(intent);
                }
            });
        }
    }
}