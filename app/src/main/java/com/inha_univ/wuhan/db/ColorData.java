package com.inha_univ.wuhan.db;

public class ColorData {
    private int idx;    //확진자 번호
    private int color;  //색

    public ColorData(){}

    public ColorData(int idx, int color){
        this.idx = idx;
        this.color = color;
    }

    public int getIdx(){
        return idx;
    }

    public int getColor(){
        return color;
    }
}
