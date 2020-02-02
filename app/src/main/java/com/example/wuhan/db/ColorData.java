package com.example.wuhan.db;

public class ColorData {
    private int idx;    //확진자 번호
    private String color;  //색

    public ColorData(){}

    public ColorData(int idx, String color){
        this.idx = idx;
        this.color = color;
    }

    public int getIdx(){
        return idx;
    }

    public String getColor(){
        return color;
    }
}
