package com.pollub.betfootball.Entity;

public class TeamUsers {
    public String userID;
    public String teamID;
    public String teamName;

    public TeamUsers(String userID, String teamID, String teamName) {
        this.userID = userID;
        this.teamID = teamID;
        this.teamName = teamName;
    }

    public TeamUsers() {
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getTeamID() {
        return teamID;
    }

    public void setTeamID(String teamID) {
        this.teamID = teamID;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
}

