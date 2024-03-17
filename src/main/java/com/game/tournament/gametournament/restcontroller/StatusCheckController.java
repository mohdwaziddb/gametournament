package com.game.tournament.gametournament.restcontroller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatusCheckController {
    @GetMapping("/status")
    public String statusCheck(){
        return "working fine";
    }
}
