package com.game.tournament.gametournament.repository;

import com.game.tournament.gametournament.model.WinnerPrizesDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WinnerPrizesDetailRepo extends JpaRepository<WinnerPrizesDetail, Long> {

    List<WinnerPrizesDetail> findAllByTournamentid(Long tournamentid);

    void deleteAllByTournamentid(Long tournament);

}
