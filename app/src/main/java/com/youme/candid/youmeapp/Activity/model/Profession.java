package com.youme.candid.youmeapp.Activity.model;

public class Profession {

    String profession;
    boolean selected = false;
    int position;

    public Profession(String profession, int position) {
        this.profession = profession;
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public Profession(String not_working)
    {
        this.profession = not_working;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
