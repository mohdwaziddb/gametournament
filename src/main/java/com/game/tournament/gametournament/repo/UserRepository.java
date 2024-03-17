package com.game.tournament.gametournament.repo;

import com.game.tournament.gametournament.entity.Role;
import com.game.tournament.gametournament.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository <User,Long>{
    Optional<User> findByUsername(String username);

    @Query(value = "update User e set e.role=:rolename where e.username=:username")
    void updateUserRole(@Param("username") String username ,@Param("rolename") Role rolename);
}
