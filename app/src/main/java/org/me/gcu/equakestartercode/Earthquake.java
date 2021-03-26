//Aidan Rooney - S1911669
package org.me.gcu.equakestartercode;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//model for Earthquake info
public class Earthquake implements Serializable {
    //declare attributes
    private String title;
    private String description;
    private LocalDate date;
    private double lat;
    private double lon;

    //default values for object
    @RequiresApi(api = Build.VERSION_CODES.O)
    public Earthquake() {
        this.title = "";
        this.description = "";
        this.date = LocalDate.now();
        this.lat = 0.00;
        this.lon = 0.00;
    }

    //get earthquake depth
    public double getDepth() {
        //split string by semi-colon
        List<String> split = new ArrayList<String>(Arrays.asList(description.split(";")));
        try {
            //use substring to grab the depth value, and parse to double
            return Double.parseDouble(split.get(3).substring(8, split.get(3).length() - 4));
        } catch (Exception e) {
            //return 0 on fail
            return 0;
        }
    }

    //get isolated earthquake location string
    public String getLocation() {
        //split string by semi-colon
        List<String> split = new ArrayList<String>(Arrays.asList(description.split(";")));

        //trim initial whitespace
        String location = split.get(1).substring(1);

        //exceptional case for when earthquake API does not specify a location
        if(location.equals("Location:  ")) {
            return "Location: LOCATION NOT SPECIFIED";
        }
        return location;
    }

    //get magnitude value
    public double getMagnitude() {
        //split string by semi-colon
        List<String> split = new ArrayList<String>(Arrays.asList(description.split(";")));
        try {
            //use substring to grab the magnitude value, and parse to double
            return Double.parseDouble(split.get(4).substring(split.get(4).length() - 3));
        } catch (Exception e) {
            //return 0 on fail
            return 0;
        }
    }

    //parse date as string
    public String getStringDate() {
        //format MONDAY, 1 JANUARY 2021
        return date.getDayOfWeek() + ", " + date.getDayOfMonth() + " " + date.getMonth() + " " + date.getYear();
    }

    //Title functions
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    //Description functions
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    //Date functions
    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }

    //Latitude functions
    public double getLat() {
        return lat;
    }
    public void setLat(double lat) {
        this.lat = lat;
    }

    //Longitude functions
    public double getLon() {
        return lon;
    }
    public void setLon(double lon) {
        this.lon = lon;
    }
}
