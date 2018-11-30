package com.youme.candid.youmeapp.Activity.model;

public class Chat_Message {

    //String chat_msg_id;
    String chat_user_id;
    String chat_receiver_id;
    String chat_message;
    String chat_msg_image;
    String chat_msg_status;
    String chat_msg_send_time;

    public Chat_Message( String chat_user_id,
                        String chat_receiver_id, String chat_message,
                        String chat_msg_image, String chat_msg_status, String chat_msg_send_time) {
       // this.chat_msg_id = chat_msg_id;
        this.chat_user_id = chat_user_id;
        this.chat_receiver_id = chat_receiver_id;
        this.chat_message = chat_message;
        this.chat_msg_image = chat_msg_image;
        this.chat_msg_status = chat_msg_status;
        this.chat_msg_send_time = chat_msg_send_time;
    }

   /* public String getChat_msg_id() {
        return chat_msg_id;
    }

    public void setChat_msg_id(String chat_msg_id) {
        this.chat_msg_id = chat_msg_id;
    }*/

    public String getChat_msg_send_time() {
        return chat_msg_send_time;
    }

    public void setChat_msg_send_time(String chat_msg_send_time) {
        this.chat_msg_send_time = chat_msg_send_time;
    }

    public String getChat_user_id() {
        return chat_user_id;
    }

    public void setChat_user_id(String chat_user_id) {
        this.chat_user_id = chat_user_id;
    }

    public String getChat_receiver_id() {
        return chat_receiver_id;
    }

    public void setChat_receiver_id(String chat_receiver_id) {
        this.chat_receiver_id = chat_receiver_id;
    }

    public String getChat_message() {
        return chat_message;
    }

    public void setChat_message(String chat_message) {
        this.chat_message = chat_message;
    }

    public String getChat_msg_image() {
        return chat_msg_image;
    }

    public void setChat_msg_image(String chat_msg_image) {
        this.chat_msg_image = chat_msg_image;
    }

    public String getChat_msg_status() {
        return chat_msg_status;
    }

    public void setChat_msg_status(String chat_msg_status) {
        this.chat_msg_status = chat_msg_status;
    }
}
