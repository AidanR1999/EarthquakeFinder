package org.me.gcu.equakestartercode;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.SQLOutput;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class FilterView extends Fragment implements DatePickerDialog.OnDateSetListener {

    private ArrayList<Earthquake> earthquakes;
    private TextView dateStartText;
    private TextView dateEndText;
    private DatePickerAdapter adapter;
    private LocalDate dateStart;
    private LocalDate dateEnd;
    private RecyclerView recyclerView;
    private View view;
    private Button cmdSearch;
    private Button cmdBackToList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_filter_view, container, false);

        cmdSearch = (Button) view.findViewById(R.id.cmdSearch);
        cmdSearch.setOnClickListener(this::filterResults);

        cmdBackToList = (Button) view.findViewById(R.id.cmdBackToList);
        cmdBackToList.setOnClickListener(this::returnToList);

        Bundle args = requireArguments();
        this.earthquakes = (ArrayList<Earthquake>) args.getSerializable("ARRAYLIST");
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        recyclerView = (RecyclerView) view.findViewById(R.id.lstFiltered);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new DatePickerAdapter();
        adapter.setTargetFragment(this, 1234);

        this.dateStartText = (TextView) view.findViewById(R.id.dateStart);
        this.dateEndText = (TextView) view.findViewById(R.id.dateEnd);

        dateStartText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.setFlag(DatePickerAdapter.FLAG_START_DATE);
                adapter.show(getParentFragmentManager() , "DATE PICK");
            }
        });
        dateEndText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.setFlag(DatePickerAdapter.FLAG_END_DATE);
                adapter.show(getParentFragmentManager(), "DATE PICK");
            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void filterResults(View v) {
        if(dateStart == null || dateStart == null || dateStart.isAfter(dateEnd)) {
            Toast.makeText(view.getContext(), "Invalid Date Range", Toast.LENGTH_SHORT).show();
        }
        else {
            ArrayList<Earthquake> filtered = new ArrayList<>();
            for(int i = 0; i < earthquakes.size(); ++i) {
                LocalDate earthquakeDate = earthquakes.get(i).getDate();
                if(earthquakeDate.isBefore(dateEnd) && earthquakeDate.isAfter(dateStart)) {
                    filtered.add(earthquakes.get(i));
                }
            }
            FilteredEarthquakeListAdapter adapter = new FilteredEarthquakeListAdapter(view.getContext(), filtered);
            recyclerView.setAdapter(adapter);
        }
    }

    public void returnToList(View v) {
        Bundle args = new Bundle();
        getParentFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.frameLayout, HomeFragment.class, args)
                .commit();
    }
}