package com.game.tournament.gametournament.mcvcontroller;

import com.game.tournament.gametournament.utils.DataTypeUtility;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

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

    /*@RequestMapping(value = "/userprofile",method = {RequestMethod.GET,RequestMethod.POST})
    public String userprofile(){
        return "dashboard/editprofile";
    }*/

    @RequestMapping(value = "/userprofile", method = {RequestMethod.GET, RequestMethod.POST})
    public String userprofile(@RequestParam Map<String, String> param, ModelMap modelMap, HttpServletResponse response, HttpServletRequest request) {
        //Long id = DataTypeUtility.getForeignKeyValue(param.get("id"));
        //modelMap.addAttribute("data_api", "/rest/employeeattendance/demo");
        return "dashboard/editprofile";
    }

}
