package com.pollub.betfootball.Entity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class User {

    public String fullName, email;
    public Integer type;

    // public Integer score;
    public User() {
    }

    public User(String fullName, String email) {
        this.fullName = fullName;
        this.email = email;
        this.type = 1;
    }


    public static void updateFullName(String userID, String fullName) {
        DatabaseReference reference;
        reference = FirebaseDatabase.getInstance().getReference().child("Users");
        reference.child(userID).child("fullName").setValue(fullName);
    }

}
