package com.game.tournament.gametournament.repository;

import com.game.tournament.gametournament.model.Tournaments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;

@Repository
public interface TournamentRepo extends JpaRepository<Tournaments,Long> {

    List<Tournaments> findAllByIsdeletedIsFalseOrIsdeletedIsNullOrderByIdDesc();

    Tournaments findByIdAndIsdeletedIsFalseOrIsdeletedIsNull(Long id);
    List<Tournaments> findAllByIdIn(HashSet<Long> ids);

    @Query(value = "SELECT * FROM Tournaments t WHERE (t.isdeleted IS FALSE OR t.isdeleted IS NULL) AND (t.iscompleted IS FALSE OR t.iscompleted IS NULL)",nativeQuery = true)
    List<Tournaments> getTournaments();
}
