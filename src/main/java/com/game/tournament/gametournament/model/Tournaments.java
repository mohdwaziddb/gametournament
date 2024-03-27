package com.game.tournament.gametournament.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "tournaments")
@Getter
@Setter
public class Tournaments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String name;

    private String description;

    private Long price;

    private String date;

    private String starttime;

    private String endtime;

    private String secretcode;

    private String minimumplayer;

    private String maximumplayer;

    private String createdon;

    @ManyToMany
    @JoinTable(
            name = "tournament_games",
            joinColumns = @JoinColumn(name = "tournament_id"),
            inverseJoinColumns = @JoinColumn(name = "game_id")
    )
    private List<Games> games;

}

