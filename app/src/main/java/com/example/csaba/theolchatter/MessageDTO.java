package com.example.csaba.theolchatter;

import java.sql.Time;
import java.sql.Timestamp;

/**
 * Created by Csaba on 26-Mar-18.
 */

public class MessageDTO {
    private String message;
    private String username;
    private String timestamp;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDate() {
        return timestamp;
    }

    public void setDate(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
