package com.game.tournament.gametournament.repo;

import com.game.tournament.gametournament.entity.Games;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Games,Long> {
}
