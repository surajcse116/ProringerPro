package com.android.llc.proringer.pro.proringerpro.pojo;

/**
 * Created by su on 7/29/17.
 */

public class SetGetProjectMessage {
    String proj_id,proj_image,proj_name,project_status,project_applied_status,project_applied_date;

    public String getProj_id() {
        return proj_id;
    }

    public void setProj_id(String proj_id) {
        this.proj_id = proj_id;
    }

    public String getProj_image() {
        return proj_image;
    }

    public void setProj_image(String proj_image) {
        this.proj_image = proj_image;
    }

    public String getProj_name() {
        return proj_name;
    }

    public void setProj_name(String proj_name) {
        this.proj_name = proj_name;
    }

    public String getProject_status() {
        return project_status;
    }

    public void setProject_status(String project_status) {
        this.project_status = project_status;
    }

    public String getProject_applied_status() {
        return project_applied_status;
    }

    public void setProject_applied_status(String project_applied_status) {
        this.project_applied_status = project_applied_status;
    }

    public String getProject_applied_date() {
        return project_applied_date;
    }

    public void setProject_applied_date(String project_applied_date) {
        this.project_applied_date = project_applied_date;
    }
}