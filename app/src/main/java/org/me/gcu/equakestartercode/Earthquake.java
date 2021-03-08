package org.me.gcu.equakestartercode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Earthquake implements Serializable {
    private String title;
    private String description;
    private String link;
    private String date;
    private String category;
    private double lat;
    private double lon;

    public Earthquake() {
        this.title = "";
        this.description = "";
        this.link = "";
        this.date = "";
        this.category = "";
        this.lat = 0.00;
        this.lon = 0.00;
    }

    public double getDepth() {
        List<String> split = new ArrayList<String>(Arrays.asList(this.description.split(";")));
        return Double.parseDouble(split.get(3).substring(8, split.get(3).length() - 4));
    }

    public String getLocation() {
        List<String> split = new ArrayList<String>(Arrays.asList(this.description.split(";")));
        return split.get(1).substring(1);
    }

    public double getMagnitude() {
        List<String> split = new ArrayList<String>(Arrays.asList(this.description.split(";")));
        return Double.parseDouble(split.get(4).substring(split.get(4).length() - 3));
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }
}
