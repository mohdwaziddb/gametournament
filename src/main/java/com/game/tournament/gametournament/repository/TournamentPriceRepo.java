package com.game.tournament.gametournament.repository;

import com.game.tournament.gametournament.model.TournamentPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TournamentPriceRepo extends JpaRepository<TournamentPrice,Long> {

    List<TournamentPrice> findByOrderByIdDesc();

}
