package com.android.llc.proringer.pro.proringerpro.pojo;

import org.json.JSONArray;

/**
 * Created by su on 12/14/17.
 */

public class SetGetServicePojo {
    String id,parent_id,category_name;
    JSONArray getSubcategory;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public JSONArray getGetSubcategory() {
        return getSubcategory;
    }

    public void setGetSubcategory(JSONArray getSubcategory) {
        this.getSubcategory = getSubcategory;
    }
}
