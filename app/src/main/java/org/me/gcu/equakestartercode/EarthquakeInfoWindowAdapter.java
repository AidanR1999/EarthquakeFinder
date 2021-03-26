//Aidan Rooney - S1911669
package org.me.gcu.equakestartercode;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

//adapter for adding earthquake information to the maps info window view
public class EarthquakeInfoWindowAdapter implements GoogleMap.InfoWindowAdapter{
    //declare context
    private Context context;

    //set context
    public EarthquakeInfoWindowAdapter(Context context) {
        this.context = context;
    }

    //necessary boilerplate
    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    //display earthquake information in maps info window
    @Override
    public View getInfoContents(Marker marker) {
        //get parent view
        View view = ((Activity)context).getLayoutInflater()
                .inflate(R.layout.earthquake_info_window_adapter, null);

        //get earthquake info
        Earthquake earthquake = (Earthquake) marker.getTag();

        //get xml classes
        TextView title = view.findViewById(R.id.title);
        TextView magnitude = view.findViewById(R.id.magnitude);
        TextView date = view.findViewById(R.id.date);
        TextView latlong = view.findViewById(R.id.latlong);
        TextView depth = view.findViewById(R.id.depth);

        //display earthquake info
        title.setText(earthquake.getLocation());
        magnitude.setText("Magnitude: " + earthquake.getMagnitude());
        date.setText("Date: " + earthquake.getStringDate());
        latlong.setText(earthquake.getLat() + ", " + earthquake.getLon());
        depth.setText("Depth: " + earthquake.getDepth() + " km");

        //colour magnitude
        if(earthquake.getMagnitude() > 2) {
            magnitude.setTextColor(Color.RED);
        } else if(earthquake.getMagnitude() > 1) {
            magnitude.setTextColor(Color.parseColor("#ffa500"));
        } else {
            magnitude.setTextColor(Color.parseColor("#ffd700"));
        }

        return view;
    }
}
