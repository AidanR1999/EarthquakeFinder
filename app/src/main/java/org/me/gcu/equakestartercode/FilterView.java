package org.me.gcu.equakestartercode;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class FilterView extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private ArrayList<Earthquake> earthquakes;
    private TextView dateStartText;
    private TextView dateEndText;
    private DatePickerAdapter adapter;
    private LocalDate dateStart;
    private LocalDate dateEnd;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_view);

        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        this.earthquakes = (ArrayList<Earthquake>) args.getSerializable("ARRAYLIST");

        recyclerView = (RecyclerView) findViewById(R.id.lstFiltered);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new DatePickerAdapter();

        this.dateStartText = (TextView) findViewById(R.id.dateStart);
        this.dateEndText = (TextView) findViewById(R.id.dateEnd);

        dateStartText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                adapter.setFlag(DatePickerAdapter.FLAG_START_DATE);
                adapter.show(getSupportFragmentManager(), "DATE PICK");
            }
        });
        dateEndText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.setFlag(DatePickerAdapter.FLAG_END_DATE);
                adapter.show(getSupportFragmentManager(), "DATE PICK");
            }
        });
    }

    @Override
    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
        // Create a Calender instance
        Calendar calendar = Calendar.getInstance();

        // Set static variables of Calender instance
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,month);
        calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);

        // Get the date in form of string
        String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");

        if(adapter.getFlag() == DatePickerAdapter.FLAG_START_DATE) {
            dateStartText.setText(selectedDate);
            dateStart = LocalDate.parse(selectedDate, formatter);
        }
        else if(adapter.getFlag() == DatePickerAdapter.FLAG_END_DATE) {
            dateEndText.setText(selectedDate);
            dateEnd = LocalDate.parse(selectedDate, formatter);
        }
    }

    public void filterResults(View v) {
        if(dateStart == null || dateStart == null || dateStart.isAfter(dateEnd)) {
            Toast.makeText(this, "Invalid Date Range", Toast.LENGTH_SHORT).show();
        }
        else {
            ArrayList<Earthquake> filtered = new ArrayList<>();
            for(int i = 0; i < earthquakes.size(); ++i) {
                LocalDate earthquakeDate = earthquakes.get(i).getDate();
                if(earthquakeDate.isBefore(dateEnd) && earthquakeDate.isAfter(dateStart)) {
                    filtered.add(earthquakes.get(i));
                }
            }
            FilteredEarthquakeListAdapter adapter = new FilteredEarthquakeListAdapter(this, filtered);
            recyclerView.setAdapter(adapter);
        }
    }
}