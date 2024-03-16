package com.game.tournament.gametournament.restcontroller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatusCheckController {
    @RequestMapping(value = "/status")
    public String statusCheck(){
        return "working fine";
    }
}
