package com.android.llc.proringer.pro.proringerpro.pojo;

/**
 * Created by su on 11/1/18.
 */

public class SetgetmenuItem {
    String text;
    Integer image;
public SetgetmenuItem()
{

}
    public SetgetmenuItem(String text, Integer image) {
        this.text = text;
        this.image = image;
    }

    public String getText() {
        return text;
    }

    public Integer getImage() {
        return image;
    }
}
