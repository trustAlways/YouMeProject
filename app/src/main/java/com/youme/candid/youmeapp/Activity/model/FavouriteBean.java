package com.youme.candid.youmeapp.Activity.model;

public class FavouriteBean {

    String fav_user_id;
    String fav_user_nm;
    String fav_user_age;
    String fav_user_relationship_status;
    String fav_user_distance;
    String fav_user_date;
    String image;
    String fav_user_compatibility;


    public FavouriteBean(String fav_user_id, String fav_user_nm, String fav_user_age,
                         String fav_user_relationship_status, String fav_user_distance, String fav_user_date, String image,String fav_user_compatibility) {

        this.image = image;
        this.fav_user_id = fav_user_id;
        this.fav_user_nm = fav_user_nm;
        this.fav_user_age = fav_user_age;
        this.fav_user_relationship_status = fav_user_relationship_status;
        this.fav_user_distance = fav_user_distance;
        this.fav_user_date = fav_user_date;
        this.fav_user_compatibility = fav_user_compatibility;

    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getFav_user_id() {
        return fav_user_id;
    }

    public void setFav_user_id(String fav_user_id) {
        this.fav_user_id = fav_user_id;
    }

    public String getFav_user_nm() {
        return fav_user_nm;
    }

    public void setFav_user_nm(String fav_user_nm) {
        this.fav_user_nm = fav_user_nm;
    }

    public String getFav_user_age() {
        return fav_user_age;
    }

    public void setFav_user_age(String fav_user_age) {
        this.fav_user_age = fav_user_age;
    }

    public String getFav_user_relationship_status() {
        return fav_user_relationship_status;
    }

    public void setFav_user_relationship_status(String fav_user_relationship_status) {
        this.fav_user_relationship_status = fav_user_relationship_status;
    }

    public String getFav_user_distance() {
        return fav_user_distance;
    }

    public void setFav_user_distance(String fav_user_distance) {
        this.fav_user_distance = fav_user_distance;
    }

    public String getFav_user_date() {
        return fav_user_date;
    }

    public void setFav_user_date(String fav_user_date) {
        this.fav_user_date = fav_user_date;
    }

    public String getFav_user_compatibility() {
        return fav_user_compatibility;
    }

    public void setFav_user_compatibility(String fav_user_compatibility) {
        this.fav_user_compatibility = fav_user_compatibility;
    }
}
