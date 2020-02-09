package com.inha_univ.wuhan.db;

public class MapData {
    private String city;
    private int date;
    private int diagNum;
    private String explain;
    private int idx;
    private double latitude;
    private double longitude;


    public MapData(){}

    public MapData(String city, int date, int diagNum, String explain, int idx, float latitude, float longitude){
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

    public int getDate(){
        return date;
    }

    public int getDiagNum(){
        return diagNum;
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
