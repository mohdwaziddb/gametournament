package com.game.tournament.gametournament.service;

import com.game.tournament.gametournament.entity.Role;
import com.game.tournament.gametournament.entity.User;
import com.game.tournament.gametournament.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User saveUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);
        user.setCreatedon(LocalDateTime.now());
        return userRepository.save(user);
    }

    public Optional<User> findbyUsername(String username){
        return userRepository.findByUsername(username);
    }

    public void makeAdmin(String username){
        userRepository.updateUserRole(username,Role.ADMIN);
    }


}
