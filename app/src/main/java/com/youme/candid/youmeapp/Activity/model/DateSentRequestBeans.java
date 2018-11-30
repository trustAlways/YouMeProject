package com.youme.candid.youmeapp.Activity.model;

public class DateSentRequestBeans {
    String send_date_user_id;
    String send_date__user_nm;
    String send_date__age;
    String send_date__relationship_status;
    String send_date__distance;
    String send_date__date;
    String send_date_image;

    public DateSentRequestBeans(String send_date_user_id, String send_date__user_nm, String send_date__age,
                                String send_date__relationship_status, String send_date__distance,
                                String send_date__date, String send_date_image) {
        this.send_date_user_id = send_date_user_id;
        this.send_date__user_nm = send_date__user_nm;
        this.send_date__age = send_date__age;
        this.send_date__relationship_status = send_date__relationship_status;
        this.send_date__distance = send_date__distance;
        this.send_date__date = send_date__date;
        this.send_date_image = send_date_image;
    }

    public String getSend_date_user_id() {
        return send_date_user_id;
    }

    public void setSend_date_user_id(String send_date_user_id) {
        this.send_date_user_id = send_date_user_id;
    }

    public String getSend_date__user_nm() {
        return send_date__user_nm;
    }

    public void setSend_date__user_nm(String send_date__user_nm) {
        this.send_date__user_nm = send_date__user_nm;
    }

    public String getSend_date__age() {
        return send_date__age;
    }

    public void setSend_date__age(String send_date__age) {
        this.send_date__age = send_date__age;
    }

    public String getSend_date__relationship_status() {
        return send_date__relationship_status;
    }

    public void setSend_date__relationship_status(String send_date__relationship_status) {
        this.send_date__relationship_status = send_date__relationship_status;
    }

    public String getSend_date__distance() {
        return send_date__distance;
    }

    public void setSend_date__distance(String send_date__distance) {
        this.send_date__distance = send_date__distance;
    }

    public String getSend_date__date() {
        return send_date__date;
    }

    public void setSend_date__date(String send_date__date) {
        this.send_date__date = send_date__date;
    }

    public String getSend_date_image() {
        return send_date_image;
    }

    public void setSend_date_image(String send_date_image) {
        this.send_date_image = send_date_image;
    }
}
