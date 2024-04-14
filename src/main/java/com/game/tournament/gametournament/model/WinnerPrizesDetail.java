package com.game.tournament.gametournament.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "winner_prizes_detail")
@Getter
@Setter
@DynamicInsert
@DynamicUpdate
public class WinnerPrizesDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private Long tournamentid;

    private Long fromwinner;

    private Long towinner;

    private String prize;

    private String winneruserids;

}
