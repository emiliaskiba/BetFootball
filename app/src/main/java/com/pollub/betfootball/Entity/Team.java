package com.pollub.betfootball.Entity;

public class Team {
    public String code;
    public String name;
    public String leader;
public String prize;

    public Team(String code, String name, String leader) {
        this.code = code;
        this.name = name;
        this.leader = leader;
        this.prize = "Choose Prize";

    }

    public Team() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    public String getPrize() {
        return prize;
    }

    public void setPrize(String prize) {
        this.prize = prize;
    }
}