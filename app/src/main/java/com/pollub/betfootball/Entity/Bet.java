package com.pollub.betfootball.Entity;

public class Bet {

    public String userID;
    public String matchID;
    public Integer teamOneScore;
    public Integer teamTwoScore;
    public Integer whoWins;
    public Integer score;
    public Boolean calculated;

    public Bet(String userID, String matchID, Integer teamOneScore, Integer teamTwoScore) {
        this.userID = userID;
        this.matchID = matchID;
        this.teamOneScore = teamOneScore;
        this.teamTwoScore = teamTwoScore;

        if (teamOneScore > teamTwoScore) {
            this.whoWins = 1;
        }
        if (teamOneScore < teamTwoScore) {
            this.whoWins = 2;
        }
        if (teamOneScore == teamTwoScore) {
            this.whoWins = 0;
        }

        this.score = 0;
        this.calculated = false;
    }

    public Bet() {
    }
}
