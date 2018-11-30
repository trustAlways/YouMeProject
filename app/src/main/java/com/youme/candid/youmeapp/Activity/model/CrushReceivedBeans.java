package com.youme.candid.youmeapp.Activity.model;

public class CrushReceivedBeans {
    String receive_crush_user_id;
    String receive_crush__user_nm;
    String receive_crush__age;
    String receive_crush__relationship_status;
    String receive_crush__distance;
    String receive_crush__date;
    String receive_crush_image;
    String Receive_crush_compatible;

    public CrushReceivedBeans(String receive_crush_user_id, String receive_crush__user_nm, String receive_crush__age,
                              String receive_crush__relationship_status, String receive_crush__distance,
                              String receive_crush__date, String receive_crush_image,String Receive_crush_compatible) {
        this.receive_crush_user_id = receive_crush_user_id;
        this.receive_crush__user_nm = receive_crush__user_nm;
        this.receive_crush__age = receive_crush__age;
        this.receive_crush__relationship_status = receive_crush__relationship_status;
        this.receive_crush__distance = receive_crush__distance;
        this.receive_crush__date = receive_crush__date;
        this.receive_crush_image = receive_crush_image;
        this.Receive_crush_compatible = Receive_crush_compatible;
    }

    public String getReceive_crush_user_id() {
        return receive_crush_user_id;
    }

    public void setReceive_crush_user_id(String receive_crush_user_id) {
        this.receive_crush_user_id = receive_crush_user_id;
    }

    public String getReceive_crush__user_nm() {
        return receive_crush__user_nm;
    }

    public void setReceive_crush__user_nm(String receive_crush__user_nm) {
        this.receive_crush__user_nm = receive_crush__user_nm;
    }

    public String getReceive_crush__age() {
        return receive_crush__age;
    }

    public void setReceive_crush__age(String receive_crush__age) {
        this.receive_crush__age = receive_crush__age;
    }

    public String getReceive_crush__relationship_status() {
        return receive_crush__relationship_status;
    }

    public void setReceive_crush__relationship_status(String receive_crush__relationship_status) {
        this.receive_crush__relationship_status = receive_crush__relationship_status;
    }

    public String getReceive_crush__distance() {
        return receive_crush__distance;
    }

    public void setReceive_crush__distance(String receive_crush__distance) {
        this.receive_crush__distance = receive_crush__distance;
    }

    public String getReceive_crush__date() {
        return receive_crush__date;
    }

    public void setReceive_crush__date(String receive_crush__date) {
        this.receive_crush__date = receive_crush__date;
    }

    public String getReceive_crush_image() {
        return receive_crush_image;
    }

    public void setReceive_crush_image(String receive_crush_image) {
        this.receive_crush_image = receive_crush_image;
    }

    public String getReceive_crush_compatible() {
        return Receive_crush_compatible;
    }

    public void setReceive_crush_compatible(String receive_crush_compatible) {
        Receive_crush_compatible = receive_crush_compatible;
    }
}
