package com.pollub.betfootball.Entity;

public class Team {
    public String code;
    public String name;
    public String leader;

    public Team(String code, String name, String leader) {
        this.code = code;
        this.name = name;
        this.leader = leader;
    }

    public Team() {
    }
}