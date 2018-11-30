package com.youme.candid.youmeapp.Activity.model;

public class CardItem {

     String profile_pic;
     String name;
     String age;
     String location;
     String user_id;

    public CardItem(String user_id, String pseudonym, String age, String distance, String profilepic)
    {
        this.name = pseudonym;
        this.age = age;
        this.location = distance;
        this.user_id = user_id;
        this.profile_pic = profilepic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }
}
