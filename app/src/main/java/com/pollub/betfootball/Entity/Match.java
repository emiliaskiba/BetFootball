package com.pollub.betfootball.Entity;

public class Match {
    public Integer matchday;
    public Integer teamOneID;
    public Integer teamTwoID;
    public Integer teamOneScore;
    public Integer teamTwoScore;
    public Boolean happened;
    public Integer whoWins;
    public Boolean betBlocked;

    public Match(Integer matchday, Integer teamOneID, Integer teamTwoID) {
        this.matchday = matchday;
        this.teamOneID = teamOneID;
        this.teamTwoID = teamTwoID;
        this.teamOneScore = 0;
        this.teamTwoScore = 0;
        this.happened = false;
        this.whoWins = 0;
        this.betBlocked = false;
    }

    public Match() {
    }

    public Integer getMatchday() {
        return matchday;
    }

    public void setMatchday(Integer matchday) {
        this.matchday = matchday;
    }

    public Integer getTeamOneID() {
        return teamOneID;
    }

    public void setTeamOneID(Integer teamOneID) {
        this.teamOneID = teamOneID;
    }

    public Integer getTeamTwoID() {
        return teamTwoID;
    }

    public void setTeamTwoID(Integer teamTwoID) {
        this.teamTwoID = teamTwoID;
    }

    public Integer getTeamOneScore() {
        return teamOneScore;
    }

    public void setTeamOneScore(Integer teamOneScore) {
        this.teamOneScore = teamOneScore;
    }

    public Integer getTeamTwoScore() {
        return teamTwoScore;
    }

    public void setTeamTwoScore(Integer teamTwoScore) {
        this.teamTwoScore = teamTwoScore;
    }

    public Boolean getHappened() {
        return happened;
    }

    public void setHappened(Boolean happened) {
        this.happened = happened;
    }

    public Integer getWhoWins() {
        return whoWins;
    }

    public void setWhoWins(Integer whoWins) {
        this.whoWins = whoWins;
    }
}
