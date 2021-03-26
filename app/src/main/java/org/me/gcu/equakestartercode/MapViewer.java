//Aidan Rooney - S1911669
package org.me.gcu.equakestartercode;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

//fragment that handles google maps viewer
public class MapViewer extends Fragment implements OnMapReadyCallback {

    //declare variables
    private GoogleMap mMap;
    private ArrayList<Earthquake> earthquakes;
    private int index;
    private View view;

    //create fragment view
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //inflate view
        view = inflater.inflate(R.layout.activity_map_viewer, container, false);

        //get earthquake info
        Bundle args = requireArguments();
        this.earthquakes = (ArrayList<Earthquake>) args.getSerializable("earthquakes");
        this.index = (int) args.getInt("index", -1);

        return view;
    }

    //on fragment view created
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        //declare variables
        mMap = googleMap;
        LatLng def;
        Marker show = null;

        //set UI settings for map
        UiSettings mapUiSettings = mMap.getUiSettings();
        mapUiSettings.setZoomControlsEnabled(true);
        mapUiSettings.setCompassEnabled(true);

        //configure window info adapter
        EarthquakeInfoWindowAdapter adapter = new EarthquakeInfoWindowAdapter(view.getContext());
        mMap.setInfoWindowAdapter(adapter);

        //set default value if no earthquake index
        if(index == -1) {
            def = new LatLng(55, 0);
        } else {
            def = new LatLng(earthquakes.get(index).getLat(), earthquakes.get(index).getLon());
        }

        //for every earthquake
        for(int i = 0; i < earthquakes.size(); ++i) {
            //set latitude and longitude of marker
            LatLng location = new LatLng(earthquakes.get(i).getLat(), earthquakes.get(i).getLon());

            //set colour of marker via magnitude
            float colour;
            if(earthquakes.get(i).getMagnitude() > 2) {
                colour = BitmapDescriptorFactory.HUE_RED;
            } else if(earthquakes.get(i).getMagnitude() > 1) {
                colour = BitmapDescriptorFactory.HUE_ORANGE;
            } else {
                colour = BitmapDescriptorFactory.HUE_YELLOW;
            }

            //show marker
            Marker m = mMap.addMarker(new MarkerOptions()
                    .position(location)
                    .title(earthquakes.get(i).getLocation())
                    .snippet("Magnitude: " + earthquakes.get(i).getMagnitude())
                    .icon(BitmapDescriptorFactory.defaultMarker(colour)));

            //set marker tag for info window
            m.setTag(earthquakes.get(i));

            //set shown earthquake to index if match
            if(index == i) {
                show = m;
            }
        }

        //move camera to default value
        mMap.moveCamera(CameraUpdateFactory.newLatLng(def));

        //if show isn't null, display info window and zoom on locaiton
        if(show != null) {
            show.showInfoWindow();
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(def, 10));
        }

    }
}