package com.android.llc.proringer.pro.proringerpro.pojo;

import org.json.JSONArray;

/**
 * Created by su on 1/12/17.
 */

public class SetGetShowPortFolio {
    String id,pros_id,gallery_image,category_id,category_name,project_month,project_year,count_images;
    JSONArray multiple_gallery_image;

    public JSONArray getMultiple_gallery_image() {
        return multiple_gallery_image;
    }

    public void setMultiple_gallery_image(JSONArray multiple_gallery_image) {
        this.multiple_gallery_image = multiple_gallery_image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPros_id() {
        return pros_id;
    }

    public void setPros_id(String pros_id) {
        this.pros_id = pros_id;
    }

    public String getGallery_image() {
        return gallery_image;
    }

    public void setGallery_image(String gallery_image) {
        this.gallery_image = gallery_image;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getProject_month() {
        return project_month;
    }

    public void setProject_month(String project_month) {
        this.project_month = project_month;
    }

    public String getProject_year() {
        return project_year;
    }

    public void setProject_year(String project_year) {
        this.project_year = project_year;
    }

    public String getCount_images() {
        return count_images;
    }

    public void setCount_images(String count_images) {
        this.count_images = count_images;
    }
}
