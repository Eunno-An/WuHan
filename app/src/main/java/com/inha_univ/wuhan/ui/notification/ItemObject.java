package com.inha_univ.wuhan.ui.notification;


//공지사항 아이템 클래스
public class ItemObject {
    private String id;
    private String title;
    private String url;
    private String day;


    public ItemObject(String id, String title, String url,String day){
        this.title = title;
        this.url = url;
        this.id = id;
        this.day = day;
    }


    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }
    public String getId() {
        return id;
    }
    public String getDay(){ return day;}
}
