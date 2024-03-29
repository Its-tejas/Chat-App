package com.mydroid.chatapp.Models;

public class MessageModel {

    String UId, message, messageId;
    Long timestamp;

    public MessageModel() {
    }

    public MessageModel(String UId, String message) {
        this.UId = UId;
        this.message = message;
    }

    public MessageModel(String UId, String message, Long timestamp) {
        this.UId = UId;
        this.message = message;
        this.timestamp = timestamp;
    }

    public MessageModel(String UId, String message, String messageId, Long timestamp) {
        this.UId = UId;
        this.message = message;
        this.messageId = messageId;
        this.timestamp = timestamp;
    }

    public String getUId() {
        return UId;
    }

    public void setUId(String UId) {
        this.UId = UId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
