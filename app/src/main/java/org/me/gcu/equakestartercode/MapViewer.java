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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapViewer extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ArrayList<Earthquake> earthquakes;
    private int index = -1;
    private View view;
    private Button cmdListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_map_viewer, container, false);

        cmdListView = (Button) view.findViewById(R.id.cmdListView);
        cmdListView.setOnClickListener(this::OnClick);

        Bundle args = requireArguments();
        this.earthquakes = (ArrayList<Earthquake>) args.getSerializable("ARRAYLIST");
        this.index = (int) args.getInt("index");
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng def;
        Marker show = null;

        //config
        EarthquakeInfoWindowAdapter adapter = new EarthquakeInfoWindowAdapter(view.getContext());
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
        Bundle args = new Bundle();
        getParentFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.frameLayout, HomeFragment.class, args)
                .commit();
    }
}