package org.me.gcu.equakestartercode;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapViewer extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ArrayList<Earthquake> earthquakes;
    private int index = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_viewer);

        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        index = intent.getIntExtra("index", -1);

        earthquakes = (ArrayList<Earthquake>) args.getSerializable("ARRAYLIST");

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng def;
        Marker show = null;

        //config
        EarthquakeInfoWindowAdapter adapter = new EarthquakeInfoWindowAdapter(this);
        mMap.setInfoWindowAdapter(adapter);

        if(index == -1) {
            def = new LatLng(55, 0);
        } else {
            def = new LatLng(earthquakes.get(index).getLat(), earthquakes.get(index).getLon());
        }

        for(int i = 0; i < earthquakes.size(); ++i) {


            LatLng location = new LatLng(earthquakes.get(i).getLat(), earthquakes.get(i).getLon());

            float colour;
            if(earthquakes.get(i).getMagnitude() > 2) {
                colour = BitmapDescriptorFactory.HUE_RED;
            } else if(earthquakes.get(i).getMagnitude() > 1) {
                colour = BitmapDescriptorFactory.HUE_ORANGE;
            } else {
                colour = BitmapDescriptorFactory.HUE_YELLOW;
            }

            Marker m = mMap.addMarker(new MarkerOptions()
                    .position(location)
                    .title(earthquakes.get(i).getLocation())
                    .snippet("Magnitude: " + earthquakes.get(i).getMagnitude())
                    .icon(BitmapDescriptorFactory.defaultMarker(colour)));

            m.setTag(earthquakes.get(i));

            if(index == i) {
                show = m;
            }
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLng(def));
        if(show != null) {
            show.showInfoWindow();
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(def, 10));
        }

    }

    public void OnClick(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}