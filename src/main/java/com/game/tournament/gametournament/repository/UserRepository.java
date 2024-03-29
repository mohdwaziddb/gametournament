package com.game.tournament.gametournament.repository;


import com.game.tournament.gametournament.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Integer> {

    Optional<Users> findByUsername(String username);

    List<Users> findAllByMobileno(Long mobileno);
    List<Users> findAllByEmailid(String emailid);
    List<Users> findAllByUsername(String username);
}
