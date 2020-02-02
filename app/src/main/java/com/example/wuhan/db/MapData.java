package com.example.wuhan.db;

public class MapData {
    private String city;
    private String date;
    private String explain;
    private int idx;
    private double latitude;
    private double longitude;

    public MapData(){}

    public MapData(String city, String date, String explain, int idx, float latitude, float longitude){
        this.city = city;
        this.date = date;
        this.explain = explain;
        this.idx = idx;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getCity(){
        return city;
    }

    public String getDate(){
        return date;
    }

    public String getExplain(){
        return explain;
    }

    public int getIdx(){
        return idx;
    }

    public double getLatitude(){
        return latitude;
    }

    public double getLongitude(){
        return longitude;
    }
}
