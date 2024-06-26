package com.game.tournament.gametournament.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.List;

@Entity
@Table(name = "tournaments")
@Getter
@Setter
@DynamicInsert
@DynamicUpdate
public class Tournaments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String name;

    private String description;

    private Long priceid;

    private String date;

    private String starttime;

    private String endtime;

    private String secretcode;

    private Long minimumplayer;

    private Long maximumplayer;

    private String createdon;

    private String attachment;
//    @Lob
//    private byte[] attachment;

    private Long gameid;

    private Boolean isdeleted;

    private Boolean iscompleted;

    /*private String winnerprizes;*/

    @Transient
    private String price;

    @Transient
    private String game;

    @Transient
    private Boolean isjoin;

}

