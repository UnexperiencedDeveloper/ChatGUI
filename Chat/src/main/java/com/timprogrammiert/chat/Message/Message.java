package com.timprogrammiert.chat.Message;

import java.io.Serializable;

/**
 * created by tmatz on 03.10.2023
 */
public class Message implements Serializable {
    private String message;
    public final String usernameSentFrom;
    private Boolean connectionStillActive = true; // Information for Server

    public Message() {
        usernameSentFrom = "TimMatzenauer";
    }

    public String getMessage(){
        return message;
    }

    public void setText(String message){
        this.message = message;
    }

    public Boolean getConnectionStillActive() {
        return connectionStillActive;
    }

    public void setConnectionStillActive(Boolean connectionStillActive) {
        this.connectionStillActive = connectionStillActive;
    }
}
