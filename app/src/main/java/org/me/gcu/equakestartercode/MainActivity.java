package org.me.gcu.equakestartercode;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.lang.reflect.Array;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import org.me.gcu.equakestartercode.R;

public class MainActivity extends AppCompatActivity
{
    private TextView rawDataDisplay;
    private Button startButton;
    private String result = "";
    private String url1="";
    private String urlSource="http://quakes.bgs.ac.uk/feeds/MhSeismology.xml";
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e("MyTag","in onCreate");
        // Set up the raw links to the graphical components
        recyclerView = (RecyclerView) findViewById(R.id.lstEarthquake);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        Log.e("MyTag","after startButton");
        // More Code goes here
        startProgress();
    }

    public void startProgress()
    {
        // Run network access on a separate thread;
        new Thread(new Task(urlSource)).start();
    } //

    // Need separate thread to access the internet resource over network
    // Other neater solutions should be adopted in later iterations.
    private class Task implements Runnable
    {
        private String url;

        public Task(String aurl)
        {
            url = aurl;
        }
        @Override
        public void run()
        {

            URL aurl;
            URLConnection yc;
            BufferedReader in = null;
            String inputLine = "";

            try
            {
                aurl = new URL(url);
                yc = aurl.openConnection();
                in = new BufferedReader(new InputStreamReader(yc.getInputStream()));

                int i = 0;
                while ((inputLine = in.readLine()) != null)
                {
                    result = result + inputLine;
                    i++;
                }
                in.close();
            }
            catch (IOException ae)
            {
                Log.e("MyTag", "ioexception in run");
            }

            //
            // Now that you have the xml data you can parse it
            //

            // Now update the TextView to display raw XML data
            // Probably not the best way to update TextView
            // but we are just getting started !

            MainActivity.this.runOnUiThread(new Runnable()
            {
                public void run() {
                    parseData(result);
                }
            });
        }

        public void parseData(String result) {
            try {
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(true);
                XmlPullParser xpp = factory.newPullParser();

                xpp.setInput(new StringReader(result));
                int eventType = xpp.getEventType();

                ArrayList<Earthquake> list = new ArrayList<>();
                Earthquake eq = new Earthquake();

                while(eventType != XmlPullParser.END_DOCUMENT) {
                    if (eventType == XmlPullParser.START_DOCUMENT) {
                    } else if (eventType == XmlPullParser.START_TAG) {
                        if (xpp.getName().equals("item")) {
                            eq = new Earthquake();
                        }
                        if (xpp.getName().equals("title")) {
                            eq.setTitle(xpp.nextText());
                        } else if (xpp.getName().equals("description")) {
                            eq.setDescription(xpp.nextText());
                        } else if (xpp.getName().equals("link")) {
                            eq.setLink(xpp.nextText());
                        } else if (xpp.getName().equals("date")) {
                            eq.setDate(xpp.nextText());
                        } else if (xpp.getName().equals("category")) {
                            eq.setCategory(xpp.nextText());
                        } else if (xpp.getName().equals("lat")) {
                            eq.setLat(xpp.nextText());
                        } else if (xpp.getName().equals("long")) {
                            eq.setLon(xpp.nextText());
                        }
                    } else if (eventType == XmlPullParser.END_TAG) {
                        if (xpp.getName().equals("item")) {
                            list.add(eq);
                        }
                    }
                    eventType = xpp.next();
                }
                addEarthquakesToList(list);
            }
            catch (XmlPullParserException | IOException io) {
                System.out.println(io);
            }
        }

        public void addEarthquakesToList(ArrayList<Earthquake> earthquakes) {
            //display earthquakes
            EarthquakeListAdapter adapter = new EarthquakeListAdapter(MainActivity.this, earthquakes);
            recyclerView.setAdapter(adapter);
        }

    }

}