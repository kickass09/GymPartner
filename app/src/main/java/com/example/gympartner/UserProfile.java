package com.example.gympartner;

public class UserProfile {
    private String userId;
    public String name;
    public String gender;
    public String goal;
    public String gymLocations;

    public UserProfile() {
        // Default constructor required for calls to DataSnapshot.getValue(UserProfile.class)
    }


    public UserProfile(String name, String gender, String goal, String gymLocations) {
        this.name = name;
        this.gender = gender;
        this.goal = goal;
        this.gymLocations = gymLocations;
    }
    public UserProfile(String userId, String name, String gender) {
        this.userId = userId;
        this.name = name;
        this.gender = gender;
    }

    public UserProfile(String userId, String name, String gender, String goal, String gymLocations) {
        this.userId = userId;
        this.name = name;
        this.gender = gender;
        this.goal = goal;
        this.gymLocations = gymLocations;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public String getGymLocations() {
        return gymLocations;
    }

    public void setGymLocations(String gymLocations) {
        this.gymLocations = gymLocations;
    }
}
