package com.youme.candid.youmeapp.Activity.model;

public class Usergalleryimages {

    String img_id;
    String user_id;
    String images;

    public Usergalleryimages(String gallery_image,String img_id,String user_id)
    {
        this.images = gallery_image;
        this.img_id = img_id;
        this.user_id = user_id;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getImg_id() {
        return img_id;
    }

    public void setImg_id(String img_id) {
        this.img_id = img_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
