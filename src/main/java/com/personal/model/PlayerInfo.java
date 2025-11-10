package com.personal.model;

import jakarta.persistence.*;

@Entity
@Table(name = "player_stats", indexes = {
        @Index(columnList = "id")
})
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

    public Integer getPoints() {return points;}
    public void setPoints(Integer points) {this.points = points;}

    public Integer getRebounds() {return rebounds;}
    public void setRebounds(Integer rebounds) {this.rebounds = rebounds;}

    public Integer getAssists() {return assists;}
    public void setAssists(Integer assists) {this.assists = assists;}

    public Integer getMinutes() {return minutes;}
    public void setMinutes(Integer minutes) {this.minutes = minutes;}

    public Integer getOffensivePossessions() {return offensivePossessions;}
    public void setOffensivePossessions(Integer offensivePossessions) {this.offensivePossessions = offensivePossessions;}
}
