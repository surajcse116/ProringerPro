package com.android.llc.proringer.pro.proringerpro.pojo;

/**
 * Created by su on 6/3/18.
 */

public class SetGetBusinessHour {
    String startTime,endTime,day;
    boolean check;

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setEndTime(String endTime) {

        this.endTime = endTime;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }
}
