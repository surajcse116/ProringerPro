package com.android.llc.proringer.pro.proringerpro.pojo;

/**
 * Created by su on 6/3/18.
 */

public class SetGetBusinessHour {
    String startTime,endTime,day_name,day_status,id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDay_status() {
        return day_status;
    }

    public void setDay_status(String day_status) {
        this.day_status = day_status;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getDay_name() {
        return day_name;
    }

    public void setDay_name(String day_name) {
        this.day_name = day_name;
    }

    public String getEndTime() {
        return endTime;
    }


    public void setEndTime(String endTime) {

        this.endTime = endTime;
    }
}
