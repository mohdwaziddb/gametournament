package com.game.tournament.gametournament.mcvcontroller;

import com.game.tournament.gametournament.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/mvc")
public class AdminMVCController {

    @RequestMapping(value = "/admindashboard",method = {RequestMethod.GET,RequestMethod.POST})
    public String adminDashboard(){
        return "dashboard/admindashboard";
    }

    @RequestMapping(value = "/creategame",method = {RequestMethod.GET,RequestMethod.POST})
    public String createGame(){
        return "dashboard/creategame";
    }

    @RequestMapping(value = "/createprice",method = {RequestMethod.GET,RequestMethod.POST})
    public String createPrice(){
        return "dashboard/createprice";
    }

    @RequestMapping(value = "/createtournament",method = {RequestMethod.GET,RequestMethod.POST})
    public String createTournament(){
        return "dashboard/createtournament";
    }

    @RequestMapping(value = "/addedittournamentpage",method = {RequestMethod.GET,RequestMethod.POST})
    public String addEditTournamentPage(){
        return "dashboard/addedittournamentpage";
    }

    @RequestMapping(value = "/runningtournament",method = {RequestMethod.GET,RequestMethod.POST})
    public String runningTournament(){
        return "dashboard/runningtournament";
    }

    @RequestMapping(value = "/paymenthistory",method = {RequestMethod.GET,RequestMethod.POST})
    public String paymentHistory(){
        return "dashboard/paymenthistory";
    }

}
