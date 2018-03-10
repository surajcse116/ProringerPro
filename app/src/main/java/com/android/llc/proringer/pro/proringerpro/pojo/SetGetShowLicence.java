package com.android.llc.proringer.pro.proringerpro.pojo;

/**
 * Created by su on 30/11/17.
 */

public class SetGetShowLicence {
    String id;
    String pros_id;
    String image_info;
    String category_id;

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

    public String getImage_info() {
        return image_info;
    }

    public void setImage_info(String image_info) {
        this.image_info = image_info;
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

    public String getLicense_issuer() {
        return license_issuer;
    }

    public void setLicense_issuer(String license_issuer) {
        this.license_issuer = license_issuer;
    }

    public String getLicenses_no() {
        return licenses_no;
    }

    public void setLicenses_no(String licenses_no) {
        this.licenses_no = licenses_no;
    }

    public String getDate_expire() {
        return date_expire;
    }

    public void setDate_expire(String date_expire) {
        this.date_expire = date_expire;
    }

    public String getDate_upload() {
        return date_upload;
    }

    public void setDate_upload(String date_upload) {
        this.date_upload = date_upload;
    }

    String category_name;
    String license_issuer;
    String licenses_no;
    String date_expire;
    String date_upload;
}
