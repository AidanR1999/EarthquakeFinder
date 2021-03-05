package org.me.gcu.equakestartercode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Earthquake {
    private String title;
    private String description;
    private String link;
    private String date;
    private String category;
    private String lat;
    private String lon;

    public Earthquake() {
        this.title = "";
        this.description = "";
        this.link = "";
        this.date = "";
        this.category = "";
        this.lat = "";
        this.lon = "";
    }

    public Earthquake(String title, String description, String link, String date, String category, String lat, String lon) {
        this.title = title;
        this.description = description;
        this.link = link;
        this.date = date;
        this.category = category;
        this.lat = lat;
        this.lon = lon;
    }

    public String getLocation() {
        List<String> split = new ArrayList<String>(Arrays.asList(this.description.split(";")));
        return split.get(1);
    }

    public String getMagnitude() {
        List<String> split = new ArrayList<String>(Arrays.asList(this.description.split(";")));
        return split.get(4);
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

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }
}
