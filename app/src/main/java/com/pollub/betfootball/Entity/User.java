package com.pollub.betfootball.Entity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class User {

    public String fullName, email;
    public Integer type, scoreMatchDay, scoreAllSeason;

    // public Integer score;
    public User() {
    }

    public User(String fullName, String email) {
        this.fullName = fullName;
        this.email = email;
        this.type = 1;
        this.scoreMatchDay = 0;
        this.scoreAllSeason = 0;
    }


    public static void updateFullName(String userID, String fullName) {
        DatabaseReference reference;
        reference = FirebaseDatabase.getInstance().getReference().child("Users");
        reference.child(userID).child("fullName").setValue(fullName);
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getScoreMatchDay() {
        return scoreMatchDay;
    }


    public void setScoreMatchDay(Integer scoreMatchDay) {
        this.scoreMatchDay = scoreMatchDay;
    }

    public Integer getScoreAllSeason() {
        return scoreAllSeason;
    }

    public void setScoreAllSeason(Integer scoreAllSeason) {
        this.scoreAllSeason = scoreAllSeason;
    }
}
