package com.game.tournament.gametournament.controller;

import com.game.tournament.gametournament.service.GameService;
import com.game.tournament.gametournament.utils.GeneralResponse;
import com.game.tournament.gametournament.utils.MobileResponseDTOFactory;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/rest/game/")
public class GameRestController {

    @Autowired
    private MobileResponseDTOFactory mobileResponseDTOFactory;
    @Autowired
    private GameService gameService;



    @PostMapping("/creategame")
    public ResponseEntity<?> createGame(@RequestBody Map<String, Object> param) {
        try {
            //return new ResponseEntity<>(new GeneralResponse<>(true, "Successfully", gameService.createGame(param)), HttpStatus.OK);
            return gameService.createGame(param);
        } catch (Exception e) {
            return mobileResponseDTOFactory.reportInternalServerError(e);
        }
    }

    @GetMapping("/games_list")
    public Object gamesList(@RequestParam Map<String, Object> param) {
        try {
            return new ResponseEntity<>(new GeneralResponse<>(true, "Successfully", gameService.gamesList(param)), HttpStatus.OK);
            //return gameService.gamesList(param);
        } catch (Exception e) {
            return mobileResponseDTOFactory.reportInternalServerError(e);
        }
    }


    @PostMapping("/deletegame")
    public ResponseEntity<?> deleteGame(@RequestBody Map<String, Object> param) {
        try {
            return gameService.deleteGame(param);
        } catch (Exception e) {
            return mobileResponseDTOFactory.reportInternalServerError(e);
        }
    }

    @PostMapping("/createprice")
    public ResponseEntity<?> createPrice(@RequestBody Map<String, Object> param) {
        try {
            return gameService.createPrice(param);
        } catch (Exception e) {
            return mobileResponseDTOFactory.reportInternalServerError(e);
        }
    }

    @GetMapping("/price_list")
    public Object priceList(@RequestParam Map<String, Object> param) {
        try {
            return new ResponseEntity<>(new GeneralResponse<>(true, "Successfully", gameService.priceList(param)), HttpStatus.OK);
        } catch (Exception e) {
            return mobileResponseDTOFactory.reportInternalServerError(e);
        }
    }

    @PostMapping("/deleteprice")
    public ResponseEntity<?> deletePrice(@RequestBody Map<String, Object> param) {
        try {
            return gameService.deletePrice(param);
        } catch (Exception e) {
            return mobileResponseDTOFactory.reportInternalServerError(e);
        }
    }

    @PostMapping("/createtournament")
    public ResponseEntity<?> createTournament(@RequestParam(value = "file", required = false) MultipartFile file, @RequestParam Map<String, Object> param) {
        try {
            return gameService.createTournament(file,param);
        } catch (Exception e) {
            return mobileResponseDTOFactory.reportInternalServerError(e);
        }
    }


    @GetMapping("/get_tournaments")
    public Object getTournaments(@RequestParam Map<String, Object> param) {
        try {
            return new ResponseEntity<>(new GeneralResponse<>(true, "Successfully", gameService.getTournaments(param)), HttpStatus.OK);
        } catch (Exception e) {
            return mobileResponseDTOFactory.reportInternalServerError(e);
        }
    }

    @PostMapping("/deletetournament")
    public ResponseEntity<?> deleteTournament(@RequestBody Map<String, Object> param) {
        try {
            return gameService.deleteTournament(param);
        } catch (Exception e) {
            return mobileResponseDTOFactory.reportInternalServerError(e);
        }
    }
    //@PreAuthorize("hasRole('Admin')")
    @GetMapping("/get_tournaments_foruser")
    public Object getTournamentsForUser(@RequestParam Map<String, Object> param, HttpServletRequest request) {
        try {
            return new ResponseEntity<>(new GeneralResponse<>(true, "Successfully", gameService.getTournamentsForUser(param,request)), HttpStatus.OK);
        } catch (Exception e) {
            return mobileResponseDTOFactory.reportInternalServerError(e);
        }
    }


    @PostMapping("/joining_tournament")
    public ResponseEntity<?> joiningTournament(@RequestBody Map<String, Object> param, HttpServletRequest request) {
        try {
            return gameService.joiningTournament(param,request);
        } catch (Exception e) {
            return mobileResponseDTOFactory.reportInternalServerError(e);
        }
    }

    @GetMapping("/createtransaction")
    public Object createTransaction(@RequestParam Map<String,Object> param,HttpServletRequest request) {
        try {
            return gameService.createTransaction(param,request);
        } catch (Exception e) {
            return mobileResponseDTOFactory.reportInternalServerError(e);
        }
    }

    @GetMapping("/getplayerdetails")
    public Object getPlayerDetails(@RequestParam Map<String, Object> param, HttpServletRequest request) {
        try {
            return new ResponseEntity<>(new GeneralResponse<>(true, "Successfully", gameService.getPlayerDetails(param,request)), HttpStatus.OK);
        } catch (Exception e) {
            return mobileResponseDTOFactory.reportInternalServerError(e);
        }
    }

    @GetMapping("/getuserdetailsbyid")
    public Object getUserDetailsById(@RequestParam Map<String, Object> param, HttpServletRequest request) {
        try {
            return gameService.getUserDetailsById(param,request);
        } catch (Exception e) {
            return mobileResponseDTOFactory.reportInternalServerError(e);
        }
    }

    @PostMapping("/saveuserdetailsbyid")
    public ResponseEntity<?> saveUserDetailsById(@RequestBody Map<String, Object> param, HttpServletRequest request) {
        try {
            return gameService.saveUserDetailsById(param,request);
        } catch (Exception e) {
            return mobileResponseDTOFactory.reportInternalServerError(e);
        }
    }




}
