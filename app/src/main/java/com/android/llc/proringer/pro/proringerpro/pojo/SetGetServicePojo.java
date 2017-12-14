package com.android.llc.proringer.pro.proringerpro.pojo;

import org.json.JSONArray;

/**
 * Created by su on 12/14/17.
 */

public class SetGetServicePojo {
    String category_name,parent_category_id;
    JSONArray service_category_list;

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getParent_category_id() {
        return parent_category_id;
    }

    public void setParent_category_id(String parent_category_id) {
        this.parent_category_id = parent_category_id;
    }

    public JSONArray getService_category_list() {
        return service_category_list;
    }

    public void setService_category_list(JSONArray service_category_list) {
        this.service_category_list = service_category_list;
    }
}
