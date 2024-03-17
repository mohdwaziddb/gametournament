package com.game.tournament.gametournament.jwt;

import com.game.tournament.gametournament.security.UserPrincipal;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;

public interface IJwtProvider {
    String generateToken(UserPrincipal auth);
    Authentication getAuthentication(HttpServletRequest httpServletRequest);

    boolean validateToken(HttpServletRequest request);


}
