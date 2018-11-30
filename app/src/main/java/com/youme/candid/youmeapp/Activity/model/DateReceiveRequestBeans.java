package com.youme.candid.youmeapp.Activity.model;

public class DateReceiveRequestBeans {

    String received_date_user_id;
    String received_date__user_nm;
    String received_date__age;
    String received_date__relationship_status;
    String received_date__distance;
    String received_date__date;
    String received_date_image;

    public DateReceiveRequestBeans(String received_date_user_id, String received_date__user_nm, String received_date__age,
                                   String received_date__relationship_status,
                                   String received_date__distance, String received_date__date, String received_date_image) {
        this.received_date_user_id = received_date_user_id;
        this.received_date__user_nm = received_date__user_nm;
        this.received_date__age = received_date__age;
        this.received_date__relationship_status = received_date__relationship_status;
        this.received_date__distance = received_date__distance;
        this.received_date__date = received_date__date;
        this.received_date_image = received_date_image;
    }

    public String getReceived_date_user_id() {
        return received_date_user_id;
    }

    public void setReceived_date_user_id(String received_date_user_id) {
        this.received_date_user_id = received_date_user_id;
    }

    public String getReceived_date__user_nm() {
        return received_date__user_nm;
    }

    public void setReceived_date__user_nm(String received_date__user_nm) {
        this.received_date__user_nm = received_date__user_nm;
    }

    public String getReceived_date__age() {
        return received_date__age;
    }

    public void setReceived_date__age(String received_date__age) {
        this.received_date__age = received_date__age;
    }

    public String getReceived_date__relationship_status() {
        return received_date__relationship_status;
    }

    public void setReceived_date__relationship_status(String received_date__relationship_status) {
        this.received_date__relationship_status = received_date__relationship_status;
    }

    public String getReceived_date__distance() {
        return received_date__distance;
    }

    public void setReceived_date__distance(String received_date__distance) {
        this.received_date__distance = received_date__distance;
    }

    public String getReceived_date__date() {
        return received_date__date;
    }

    public void setReceived_date__date(String received_date__date) {
        this.received_date__date = received_date__date;
    }

    public String getReceived_date_image() {
        return received_date_image;
    }

    public void setReceived_date_image(String received_date_image) {
        this.received_date_image = received_date_image;
    }
}
