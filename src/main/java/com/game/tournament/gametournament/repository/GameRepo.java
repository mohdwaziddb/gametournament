package com.game.tournament.gametournament.repository;

import com.game.tournament.gametournament.model.Games;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepo extends JpaRepository<Games,Long> {

    List<Games> findAllByIsdeletedIsFalseOrIsdeletedIsNullOrderByIdDesc();
}
