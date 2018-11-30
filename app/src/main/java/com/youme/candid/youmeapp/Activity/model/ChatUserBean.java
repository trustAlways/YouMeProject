package com.youme.candid.youmeapp.Activity.model;

public class ChatUserBean  {

    String chat_user_id;
    String chat_user_nm;
    String chat_user_pseudonym;
    String chat_user_image;

    public ChatUserBean(String chat_user_id, String chat_user_nm, String chat_user_pseudonym, String chat_user_image) {
        this.chat_user_id = chat_user_id;
        this.chat_user_nm = chat_user_nm;
        this.chat_user_pseudonym = chat_user_pseudonym;
        this.chat_user_image = chat_user_image;
    }

    public String getChat_user_id() {
        return chat_user_id;
    }

    public void setChat_user_id(String chat_user_id) {
        this.chat_user_id = chat_user_id;
    }

    public String getChat_user_nm() {
        return chat_user_nm;
    }

    public void setChat_user_nm(String chat_user_nm) {
        this.chat_user_nm = chat_user_nm;
    }

    public String getChat_user_pseudonym() {
        return chat_user_pseudonym;
    }

    public void setChat_user_pseudonym(String chat_user_pseudonym) {
        this.chat_user_pseudonym = chat_user_pseudonym;
    }

    public String getChat_user_image() {
        return chat_user_image;
    }

    public void setChat_user_image(String chat_user_image) {
        this.chat_user_image = chat_user_image;
    }
}
