package com.example.home;

public class SearchDiary {

    private String id,time_period_id,date,title,text;



    public SearchDiary(String id, String time_period_id, String date,String title, String text) {
        this.id = id;
        this.time_period_id = time_period_id;
        this.date = date;
        this.title = title;
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime_period_id() {
        return time_period_id;
    }

    public void setTime_period_id(String time_period_id) {
        this.time_period_id = time_period_id;
    }

    public String getdate() {
        return date;
    }

    public void setdate(String date) {
        this.date = date;
    }

    public String gettitle() {
        return title;
    }

    public void settitle(String title) {
        this.title = title;
    }


    public String gettext() {
        return text;
    }

    public void settext(String text) {
        this.text = text;
    }
}
