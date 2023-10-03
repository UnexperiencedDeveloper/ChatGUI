package com.timprogrammiert.chat.Message;

import java.io.Serializable;

/**
 * created by tmatz on 03.10.2023
 */
public class Message implements Serializable {
    private String message;

    public String GetMessage(){
        return message;
    }

    public void SetMessage(String message){
        this.message = message;
    }
}
