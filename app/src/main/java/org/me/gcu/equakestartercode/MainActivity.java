//Aidan Rooney - S1911669
package org.me.gcu.equakestartercode;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.io.StringReader;
import java.lang.reflect.Array;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//main activity for holding fragments
public class MainActivity extends AppCompatActivity
{
    //declare saved variables for persistence
    private ArrayList<Earthquake> earthquakes;
    private int fragment = 0;
    private int earthquakeSelected = -1;

    //create activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //configure bottom navbar listener
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        //retrieve data from API
        startProgress();
    }

    //save data when rotating device
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putSerializable("earthquakes", (Serializable) earthquakes);
        savedInstanceState.putInt("fragment", fragment);
        savedInstanceState.putInt("earthquakeSelected", fragment);
    }

    //load data before calling onCreate when rotating device
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        earthquakes = (ArrayList<Earthquake>) savedInstanceState.getSerializable("earthquakes");
        fragment = (int) savedInstanceState.getInt("fragment", 0);
        earthquakeSelected = (int) savedInstanceState.getInt("earthquakeSelected", -1);
    }

    //listener for bottom navigation menu
    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        //when item on navbar is selected
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            //load earthquakes into arguments
            Bundle args = new Bundle();
            args.putSerializable("earthquakes", (Serializable) earthquakes);

            //determine which item has been selected, and load appropriate fragment
            switch (item.getItemId()) {
                case R.id.Home:
                    loadHomeFragment();
                    break;
                case R.id.Map:
                    loadMapFragment();
                    break;
                case R.id.Filter:
                    loadFilterFragment();
                    break;
            }

            return true;
        }
    };

    //sets the current fragment on screen
    public void setFragment(int i) {
        fragment = i;
    }

    //sets the current earthquake that is selected
    public void setEarthquakeSelected(int i) {
        earthquakeSelected = i;
    }

    //loads the home fragment onto the frame layout
    private void loadHomeFragment() {
        //set current fragment
        fragment = 0;

        //set arguments
        Bundle args = new Bundle();
        args.putSerializable("earthquakes", (Serializable) earthquakes);

        //replace frame layout with home fragment
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.frameLayout, HomeFragment.class, args)
                .commit();
    }

    //loads the map fragment onto the frame layout
    private void loadMapFragment() {
        //set current fragment
        fragment = 1;

        //set arguments
        Bundle args = new Bundle();
        args.putSerializable("earthquakes", (Serializable) earthquakes);
        args.putSerializable("index", earthquakeSelected);

        //replace frame layout with map fragment
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.frameLayout, MapViewer.class, args)
                .commit();
    }

    //loads the filter fragment onto the frame layout
    private void loadFilterFragment() {
        //set current fragment
        fragment = 2;

        //set arguments
        Bundle args = new Bundle();
        args.putSerializable("earthquakes", (Serializable) earthquakes);

        //replace frame layout with filter fragment
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.frameLayout, FilterView.class, args)
                .commit();
    }

    //starts retrieving progress on another thread
    public void startProgress()
    {
        //set url
        String urlSource="http://quakes.bgs.ac.uk/feeds/MhSeismology.xml";

        //execute task
        new getEarthquakeDataTask().execute(urlSource);
    }

    //retrieves raw string output from API
    private class getEarthquakeDataTask extends AsyncTask<String, Void, String>{
        protected String doInBackground(String... url) {
            //declare variables
            URL aurl;
            URLConnection yc;
            BufferedReader in = null;
            String inputLine = "";
            String result = "";

            try
            {
                //create URL
                aurl = new URL(url[0]);

                //open connection to API
                yc = aurl.openConnection();

                //read data
                in = new BufferedReader(new InputStreamReader(yc.getInputStream()));

                //add data to string
                int i = 0;
                while ((inputLine = in.readLine()) != null)
                {
                    result = result + inputLine;
                    i++;
                }

                //close connection
                in.close();
                return result;
            }
            catch (IOException ae)
            {
                Log.e("MyTag", "ioexception in run");
                return "error";
            }
        }

        //once data has been received, run parse data task
        protected void onPostExecute(String result) {
            //run next task to parse data
            new parseEarthquakeDataTask().execute(result);
        }
    }

    //parses the raw string data from API
    private class parseEarthquakeDataTask extends AsyncTask<String, Void, ArrayList<Earthquake>>
    {
        @RequiresApi(api = Build.VERSION_CODES.O)
        protected ArrayList<Earthquake> doInBackground(String... params) {
            try {
                //set result to first param
                String result = params[0];

                //configure pull parser
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(true);
                XmlPullParser xpp = factory.newPullParser();

                //configure event
                xpp.setInput(new StringReader(result));
                int eventType = xpp.getEventType();

                //instantiate earthquake data
                ArrayList<Earthquake> list = new ArrayList<>();
                Earthquake eq = new Earthquake();

                //for every XML tag in result, retrieve data and store in list
                while(eventType != XmlPullParser.END_DOCUMENT) {
                    if (eventType == XmlPullParser.START_DOCUMENT) {
                    } else if (eventType == XmlPullParser.START_TAG) {
                        //create new earthquake if new item is detected
                        if (xpp.getName().equals("item")) {
                            eq = new Earthquake();
                        }
                        //get earthquake title
                        if (xpp.getName().equals("title")) {
                            eq.setTitle(xpp.nextText());
                        }
                        //get earthquake description
                        else if (xpp.getName().equals("description")) {
                            String desc = xpp.nextText();
                            eq.setDescription(desc);
                        }
                        //get earthquake date and format into LocalDate object
                        else if (xpp.getName().equals("pubDate")) {
                            //extract earthquake date from value
                            String text = xpp.nextText();
                            String extractedString = text.substring(text.indexOf(",") + 2, text.length() - 9);

                            //format string into correct format
                            List<String> split = new ArrayList<String>(Arrays.asList(extractedString.split(" ")));
                            String formatted = split.get(0) + "-" + split.get(1) + "-" + split.get(2);

                            //configure formatter and parse date value
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
                            LocalDate date = LocalDate.parse(formatted, formatter);
                            eq.setDate(date);
                        }
                        //get earthquake latitude and parse into double
                        else if (xpp.getName().equals("lat")) {
                            eq.setLat(Double.parseDouble(xpp.nextText()));
                        }
                        //get earthquake longitude and parse into double
                        else if (xpp.getName().equals("long")) {
                            eq.setLon(Double.parseDouble(xpp.nextText()));
                        }
                    }
                    //add earthquake to list
                    else if (eventType == XmlPullParser.END_TAG) {
                        if (xpp.getName().equals("item")) {
                            list.add(eq);
                        }
                    }
                    //go to next tag
                    eventType = xpp.next();
                }
                return list;
            }
            catch (XmlPullParserException | IOException io) {
                System.out.println(io);
                return null;
            }
        }

        //load the UI fragment when earthquakes are parsed
        protected void onPostExecute(ArrayList<Earthquake> list) {
            //store parsed earthquakes
            earthquakes = list;

            //load UI
            if(fragment == 1) {
                loadMapFragment();
            }
            else if(fragment == 2){
                loadFilterFragment();
            } else {
                loadHomeFragment();
            }
        }
    }
}