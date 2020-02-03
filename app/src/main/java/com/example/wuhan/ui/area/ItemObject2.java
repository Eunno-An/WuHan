package com.example.wuhan.ui.area;


//공지사항 아이템 클래스
public class ItemObject2 {
    private String date;
    private String place;
    private String comment;



    public ItemObject2(String date, String place, String comment){
        this.place = place;
        this.comment = comment;
        this.date = date;
    }


    public String getPlace() {
        return place;
    }
    public String getComment() {
        return comment;
    }
    public String getDate() {
        return date;
    }

}
