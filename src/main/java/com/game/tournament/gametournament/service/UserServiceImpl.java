package com.game.tournament.gametournament.service;

import com.game.tournament.gametournament.entity.UserDetailsModal;
import com.game.tournament.gametournament.repo.UserDetailsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl {

    @Autowired
    private UserDetailsRepo userDetailsRepo;

    public ResponseEntity<?> addUser(UserDetailsModal userDetailsModal){
        userDetailsRepo.save(userDetailsModal);
        return ResponseEntity.ok(userDetailsModal);
    }

}
