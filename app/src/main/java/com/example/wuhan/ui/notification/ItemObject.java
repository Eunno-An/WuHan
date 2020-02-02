package com.example.wuhan.ui.notification;


//공지사항 아이템 클래스
public class ItemObject {
    private String id;
    private String title;
    private String url;



    public ItemObject(String id, String title, String url){
        this.title = title;
        this.url = url;
        this.id = id;
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

}
