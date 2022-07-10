package com.example.nested_recyclerview_sample;

import java.util.List;

public class Message {
    String name;
    String imageurl;


    List<ChildData> childData;

    public Message() {
    }

    public Message(String name, String imageurl, List<ChildData> childData) {
        this.name = name;
        this.imageurl = imageurl;
        this.childData = childData;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public List<ChildData> getChildData() {
        return childData;
    }

    public void setChildData(List<ChildData> childData) {
        this.childData = childData;
    }
}