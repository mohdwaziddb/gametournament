package com.game.tournament.gametournament.repository;

import com.game.tournament.gametournament.model.Players;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerRepo extends JpaRepository <Players,Long> {

    List<Players> findAllByUserid(Long userid);

    List<Players> findAllByOrderByIdDesc();

}
