package com.game.tournament.gametournament.mcvcontroller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class IndexController {

    @RequestMapping(value = "/", method = {RequestMethod.GET, RequestMethod.POST})
    public String signup(HttpServletRequest req, ModelMap modelMap) {
        return "dashboard/signup";
    }

    @RequestMapping(value = "/login", method = {RequestMethod.GET, RequestMethod.POST})
    public String longin(HttpServletRequest req, ModelMap modelMap) {
        return "dashboard/login";
    }

    @RequestMapping(value = "/forgetpassword", method = {RequestMethod.GET, RequestMethod.POST})
    public String forgetpassword(HttpServletRequest req, ModelMap modelMap) {
        return "dashboard/forgetpassword";
    }
}
