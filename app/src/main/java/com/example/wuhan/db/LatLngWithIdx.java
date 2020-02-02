package com.example.wuhan.db;

import com.google.android.gms.maps.model.LatLng;

public class LatLngWithIdx {
    public int idx;
    public LatLng latlng;

    public LatLngWithIdx(int idx, double lat, double lng){
        this.idx = idx;
        this.latlng = new LatLng(lat, lng);
    }
}
