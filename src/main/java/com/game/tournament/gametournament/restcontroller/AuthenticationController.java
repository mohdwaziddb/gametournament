package com.game.tournament.gametournament.restcontroller;

import com.game.tournament.gametournament.entity.User;
import com.game.tournament.gametournament.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "rest/authentication")
public class AuthenticationController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public String signup(@RequestBody User user){
        userService.saveUser(user);
        return "successfully--";
    }

    @PostMapping("/login")
    public String login(){
        return "login";
    }


}
