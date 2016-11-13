package com.example.cuizehui.smartschool.entity;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

/**
 * Created by cuizehui on 2016/9/25at ${time}.
 */
public class ChatBean {

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
    public   static final int answer=1;
    public   static final int ask=2;
    private int type;
    private String string;

    public int getPic() {
        return pic;
    }

    public void setPic(int pic) {
        this.pic = pic;
    }

    private int pic;
    public int getPicID() {
        return picID;
    }

    public void setPicID(int picID) {
        this.picID = picID;
    }

    private  int picID;
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String  message;
}
