package org.me.gcu.equakestartercode;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Earthquake implements Serializable {
    private String title;
    private String description;
    private String link;
    private Date date;
    private String category;
    private String location;
    private double lat;
    private double lon;
    private double depth;
    private double magnitude;

    public Earthquake() {
        this.title = "";
        this.description = "";
        this.link = "";
        this.date = new Date(System.currentTimeMillis());
        this.category = "";
        this.lat = 0.00;
        this.lon = 0.00;
    }

    public void setDepth(String description) {
        List<String> split = new ArrayList<String>(Arrays.asList(description.split(";")));
        this.depth = Double.parseDouble(split.get(3).substring(8, split.get(3).length() - 4));
    }

    public double getDepth() { return depth; }

    public void setLocation(String description) {
        List<String> split = new ArrayList<String>(Arrays.asList(description.split(";")));
        this.location = split.get(1).substring(1);
    }

    public String getLocation() { return location; }

    public void setMagnitude(String description) {
        List<String> split = new ArrayList<String>(Arrays.asList(description.split(";")));
        this.magnitude = Double.parseDouble(split.get(4).substring(split.get(4).length() - 3));
    }

    public double getMagnitude() { return magnitude; }

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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
