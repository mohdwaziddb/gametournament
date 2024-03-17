package com.game.tournament.gametournament.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username",unique = true,nullable = false,length = 100)
    private String username;

    @Column(name = "password",nullable = false,length = 100)
    private String password;

    @Column(name = "name",unique = true,nullable = false,length = 100)
    private String name;

    @Column(name = "createdon",unique = true,nullable = false)
    private LocalDateTime createdon;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @Transient
    String token;



}
