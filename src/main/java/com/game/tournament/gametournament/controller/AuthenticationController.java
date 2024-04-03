package com.game.tournament.gametournament.controller;

import com.game.tournament.gametournament.model.AuthenticationResponse;
import com.game.tournament.gametournament.model.Users;
import com.game.tournament.gametournament.service.AuthenticationService;
import com.game.tournament.gametournament.utils.GeneralResponse;
import com.game.tournament.gametournament.utils.MobileResponseDTOFactory;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/rest/authentication/")
public class AuthenticationController {

    private final AuthenticationService authService;
    @Autowired
    private MobileResponseDTOFactory mobileResponseDTOFactory;

    public AuthenticationController(AuthenticationService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> register(@RequestBody Users request) {
        try {
            //return new ResponseEntity<>(new GeneralResponse<>(true, "Successfully", authService.register(request)), HttpStatus.OK);
            return authService.register(request);
        } catch (Exception e) {
            return mobileResponseDTOFactory.reportInternalServerError(e);
        }
    }

    @PostMapping("/login")
    public Object login(@RequestBody Users usersrequest) {
        try {
            //return new ResponseEntity<>(new GeneralResponse<>(true, "Successfully", authService.authenticate(request)), HttpStatus.OK);
            return authService.authenticate(usersrequest);
        } catch (Exception e) {
            return mobileResponseDTOFactory.reportInternalServerError(e);
        }
    }

    @PostMapping("/forgetpassword")
    public ResponseEntity<?> forgetpassword(@RequestBody Users request) {
        try {
            //return new ResponseEntity<>(new GeneralResponse<>(true, "Successfully", authService.register(request)), HttpStatus.OK);
            return authService.forgetPassword(request);
        } catch (Exception e) {
            return mobileResponseDTOFactory.reportInternalServerError(e);
        }
    }

    @PostMapping("/token_authenticate")
    public ResponseEntity<?> tokenAuthenticate(@RequestBody Map<String ,Object> param, HttpServletRequest request) {
        try {
            return authService.tokenAuthenticate(param,request);
        } catch (Exception e) {
            return mobileResponseDTOFactory.reportInternalServerError(e);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody Map<String ,Object> param, HttpServletRequest request) {
        try {
            return authService.logout(param,request);
        } catch (Exception e) {
            return mobileResponseDTOFactory.reportInternalServerError(e);
        }
    }
}
