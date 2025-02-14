package com.bechuchi.model;

public abstract class Message {
    private String ID;
    private long timestamp; // Använd long för timestamp för att hantera Unix-tid

    public Message(String inputID, long timestamp) {
        this.ID = inputID;
        this.timestamp = timestamp;
    }

    // Gemensamma metoder
    public abstract void printInfo();

    // Getters
    public String getID() {
        return ID;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
