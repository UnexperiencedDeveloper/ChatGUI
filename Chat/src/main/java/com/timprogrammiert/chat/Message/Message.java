package com.timprogrammiert.chat.Message;

import java.io.Serializable;

/**
 * created by tmatz on 03.10.2023
 */
public class Message implements Serializable {
    private String message;
    private Boolean connectionStillActive = true; // Information for Server

    public String GetMessage(){
        return message;
    }

    public void SetMessage(String message){
        this.message = message;
    }

    public Boolean getConnectionStillActive() {
        return connectionStillActive;
    }

    public void setConnectionStillActive(Boolean connectionStillActive) {
        this.connectionStillActive = connectionStillActive;
    }
}
