package com.pollub.betfootball.Entity;

public class User {

    public String fullName, email;
    public Integer type;
   // public Integer score;
    public User(){}

    public User(String fullName, String email){
        this.fullName = fullName;
        this.email = email;
        this.type = 1;
    }
}
