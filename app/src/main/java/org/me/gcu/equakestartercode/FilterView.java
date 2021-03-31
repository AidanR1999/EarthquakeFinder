//Aidan Rooney - S1911669
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

//allows the user to filter earthquakes using dates, and displays general facts about the range of earthquakes
public class FilterView extends Fragment implements DatePickerDialog.OnDateSetListener {

    //declare variables
    private ArrayList<Earthquake> earthquakes;
    private TextView dateStartText;
    private TextView dateEndText;
    private DatePickerAdapter adapter;
    private LocalDate dateStart;
    private LocalDate dateEnd;
    private RecyclerView recyclerView;
    private View view;
    private Button cmdSearch;


    //create fragment view
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //inflate view
        view = inflater.inflate(R.layout.activity_filter_view, container, false);

        //set onclick for search
        cmdSearch = (Button) view.findViewById(R.id.cmdSearch);
        cmdSearch.setOnClickListener(this::filterResults);

        //get earthquake data
        Bundle args = requireArguments();
        this.earthquakes = (ArrayList<Earthquake>) args.getSerializable("earthquakes");

        return view;
    }

    //on view is created, display earthquake info
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        //set layout manager for recycler view
        recyclerView = (RecyclerView) view.findViewById(R.id.lstFiltered);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        //set datepicker adapter
        adapter = new DatePickerAdapter();
        adapter.setTargetFragment(this, 1234);

        //init date textboxes
        this.dateStartText = (TextView) view.findViewById(R.id.dateStart);
        this.dateEndText = (TextView) view.findViewById(R.id.dateEnd);

        //display datepicker dialog on dateStart select
        dateStartText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //show datepicker
                adapter.setFlag(DatePickerAdapter.FLAG_START_DATE);
                adapter.show(getParentFragmentManager() , "DATE PICK");
            }
        });

        //display datepicker dialog on dateEnd select
        dateEndText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //show datepicker
                adapter.setFlag(DatePickerAdapter.FLAG_END_DATE);
                adapter.show(getParentFragmentManager(), "DATE PICK");
            }
        });
    }


    //on datepicker dialog selected date
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

        //use flag to differentiate between date text boxes
        if(adapter.getFlag() == DatePickerAdapter.FLAG_START_DATE) {
            //show date
            dateStartText.setText(selectedDate);

            //parse date
            dateStart = LocalDate.parse(selectedDate, formatter);
        }
        else if(adapter.getFlag() == DatePickerAdapter.FLAG_END_DATE) {
            //show date
            dateEndText.setText(selectedDate);

            //parse date
            dateEnd = LocalDate.parse(selectedDate, formatter);
        }
    }

    //filter results on click
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void filterResults(View v) {
        //ensure neither dates are null and no illegal date range has been entered
        if(dateStart == null || dateStart == null || dateStart.isAfter(dateEnd) || dateStart.isEqual(dateEnd)) {
            //show error
            Toast.makeText(view.getContext(), "Invalid Date Range", Toast.LENGTH_SHORT).show();
        }
        else {
            //filter earthquakes using dates
            ArrayList<Earthquake> filtered = new ArrayList<>();
            for(int i = 0; i < earthquakes.size(); ++i) {
                LocalDate earthquakeDate = earthquakes.get(i).getDate();
                if(earthquakeDate.isBefore(dateEnd.plusDays(1)) && earthquakeDate.isAfter(dateStart.minusDays(1))) {
                    filtered.add(earthquakes.get(i));
                }
            }

            //pass filtered earthquakes into recycler view
            FilteredEarthquakeListAdapter adapter = new FilteredEarthquakeListAdapter(view.getContext(), filtered);
            recyclerView.setAdapter(adapter);
        }
    }
}