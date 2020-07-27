package com.example.quakereport;

public class Quake {

    private double magnitude;
    private String location;
    private long time;
    private String url;

    public Quake(double magnitude,String location,long time,String url){
        this.location=location;
        this.magnitude=magnitude;
        this.time=time;
        this.url=url;
    }

    public double getMagnitude() {
        return magnitude;
    }

    public String getLocation() {
        return location;
    }

    public long getTime() {
        return time;
    }

    public String getUrl() {
        return url;
    }
}
