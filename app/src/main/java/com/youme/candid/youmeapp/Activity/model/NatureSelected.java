package com.youme.candid.youmeapp.Activity.model;

public class NatureSelected {
    private boolean isSelected;
    private String animal;


    int position;



    public String getAnimal() {
        return animal;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
    public void setAnimal(String animal) {
        this.animal = animal;
    }


    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public NatureSelected(String animal1,int position1) {
        this.animal=animal1;
        this.position=position1;

    }
}
