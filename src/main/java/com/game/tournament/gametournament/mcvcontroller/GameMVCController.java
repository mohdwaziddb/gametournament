package com.game.tournament.gametournament.mcvcontroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/game")
public class GameMVCController {

    @RequestMapping(value = "/homepage",method = {RequestMethod.GET,RequestMethod.POST})
    public String homepage(){
        return "dashboard/homepage";
    }

    @RequestMapping(value = "/tournament",method = {RequestMethod.GET,RequestMethod.POST})
    public String tournament(){
        return "dashboard/tournament";
    }

}
