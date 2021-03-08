package org.me.gcu.equakestartercode;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class FilterView extends AppCompatActivity {

    private ArrayList<Earthquake> earthquakes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_view);

        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        earthquakes = (ArrayList<Earthquake>) args.getSerializable("ARRAYLIST");
    }
}