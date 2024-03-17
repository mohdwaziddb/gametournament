package com.game.tournament.gametournament.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "games")
@Getter
@Setter
public class Games {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title",nullable = false,length = 100)
    private String title;

    @Column(name = "description",nullable = false,length = 1000)
    private String description;

    @Column(name = "createdon",nullable = false)
    private String createdon;

}
