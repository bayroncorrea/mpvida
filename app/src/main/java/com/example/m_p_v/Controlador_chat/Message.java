package com.example.m_p_v.Controlador_chat;

public class Message {
    private String sender;
    private String text;
    private long timestamp;
    private String replyTo;

    // Constructor vacío necesario para Firebase
    public Message() {}

    public Message(String sender, String text, long timestamp, String replyTo) {
        this.sender = sender;
        this.text = text;
        this.timestamp = timestamp;
        this.replyTo = replyTo;
    }

    // Getters y setters
    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
    public String getReplyTo() {
        return replyTo;
    }

    public void setReplyTo(String replyTo) {
        this.replyTo = replyTo;
    }
}
