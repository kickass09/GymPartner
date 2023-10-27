package com.example.gympartner;

public class FriendRequest {

    private String senderId;   // ID of the user who sent the request
    private String receiverId; // ID of the user who should receive the request
    private String status;     // Status of the request (e.g., "pending", "accepted", "rejected")
    private String name;
    private String goal;

    public FriendRequest() {
        // Default constructor required for Firebase
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public FriendRequest(String senderId, String receiverId, String status, String name, String goal) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.status = status;
        this.name = name;
        this.goal = goal;
    }

    public FriendRequest(String senderId, String receiverId, String status) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.status = status;
    }

    public String getSenderId() {
        return senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public String getStatus() {
        return status;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
