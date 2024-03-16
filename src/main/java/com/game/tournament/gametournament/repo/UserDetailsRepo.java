package com.game.tournament.gametournament.repo;

import com.game.tournament.gametournament.entity.UserDetailsModal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailsRepo extends JpaRepository <UserDetailsModal,Long>{
}
