package com.game.tournament.gametournament.restcontroller;

import com.game.tournament.gametournament.entity.UserDetailsModal;
import com.game.tournament.gametournament.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(value = "rest/authentication")
public class AuthenticationController {

    @Autowired
    private UserServiceImpl userService;

    @RequestMapping(value = "/signup")
    public String signup(){
        return "signup";
    }

    @RequestMapping(value = "/login")
    public String login(){
        return "login";
    }

    @RequestMapping(value = "/adduser")
    public ResponseEntity<?> adduser(@RequestParam UserDetailsModal userDetailsModal){
        return userService.addUser(userDetailsModal);
    }

}
