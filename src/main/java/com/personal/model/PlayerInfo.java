package com.personal.model;

import jakarta.persistence.*;

@Entity
@Table(name = "player_stats")
public class PlayerInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String team;
    private int points;
    private int rebounds;
    private int assists;
    private int minutes;
    private int offensivePossessions;

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public String getTeam() {return team;}
    public void setTeam(String team) {this.team = team;}

    public int getPoints() {return points;}
    public void setPoints(int points) {this.points = points;}

    public int getRebounds() {return rebounds;}
    public void setRebounds(int rebounds) {this.rebounds = rebounds;}

    public int getAssists() {return assists;}
    public void setAssists(int assists) {this.assists = assists;}

    public int getMinutes() {return minutes;}
    public void setMinutes(int minutes) {this.minutes = minutes;}

    public int getOffensivePossessions() {return offensivePossessions;}
    public void setOffensivePossessions(int offensivePossessions) {this.offensivePossessions = offensivePossessions;}
}
