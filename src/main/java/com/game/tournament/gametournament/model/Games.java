package com.game.tournament.gametournament.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "games")
@Getter
@Setter
public class Games {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String name;

    private String description;

    private String attachment;

    private String createdon;

    @ManyToMany(mappedBy = "games")
    private List<Tournaments> tournaments;

}

