package com.youme.candid.youmeapp.Activity.model;

public class CrushRequestBeans {
    String send_crush_user_id;
    String send_crush__user_nm;
    String send_crush__age;
    String send_crush__relationship_status;
    String send_crush__distance;
    String send_crush__date;
    String send_crush_image;
    String Send_crush_compatible;
    String Send_crush_status;

    public CrushRequestBeans(String send_crush_user_id, String send_crush__user_nm, String send_crush__age,
                             String send_crush__relationship_status, String send_crush__distance, String send_crush__date,
                             String send_crush_image,String Send_crush_compatible, String Send_crush_status) {
        this.send_crush_user_id = send_crush_user_id;
        this.send_crush__user_nm = send_crush__user_nm;
        this.send_crush__age = send_crush__age;
        this.send_crush__relationship_status = send_crush__relationship_status;
        this.send_crush__distance = send_crush__distance;
        this.send_crush__date = send_crush__date;
        this.send_crush_image = send_crush_image;
        this.Send_crush_compatible = Send_crush_compatible;
        this.Send_crush_status = Send_crush_status;
    }

    public String getSend_crush_status() {
        return Send_crush_status;
    }

    public void setSend_crush_status(String send_crush_status) {
        Send_crush_status = send_crush_status;
    }

    public String getSend_crush_user_id() {
        return send_crush_user_id;
    }

    public void setSend_crush_user_id(String send_crush_user_id) {
        this.send_crush_user_id = send_crush_user_id;
    }

    public String getSend_crush__user_nm() {
        return send_crush__user_nm;
    }

    public void setSend_crush__user_nm(String send_crush__user_nm) {
        this.send_crush__user_nm = send_crush__user_nm;
    }

    public String getSend_crush__age() {
        return send_crush__age;
    }

    public void setSend_crush__age(String send_crush__age) {
        this.send_crush__age = send_crush__age;
    }

    public String getSend_crush__relationship_status() {
        return send_crush__relationship_status;
    }

    public void setSend_crush__relationship_status(String send_crush__relationship_status) {
        this.send_crush__relationship_status = send_crush__relationship_status;
    }

    public String getSend_crush__distance() {
        return send_crush__distance;
    }

    public void setSend_crush__distance(String send_crush__distance) {
        this.send_crush__distance = send_crush__distance;
    }

    public String getSend_crush__date() {
        return send_crush__date;
    }

    public void setSend_crush__date(String send_crush__date) {
        this.send_crush__date = send_crush__date;
    }

    public String getSend_crush_image() {
        return send_crush_image;
    }

    public void setSend_crush_image(String send_crush_image) {
        this.send_crush_image = send_crush_image;
    }

    public String getSend_crush_compatible() {
        return Send_crush_compatible;
    }

    public void setSend_crush_compatible(String send_crush_compatible) {
        Send_crush_compatible = send_crush_compatible;
    }
}
