package com.game.tournament.gametournament.jwt;

import com.game.tournament.gametournament.Utils.SecurityUtils;
import com.game.tournament.gametournament.security.UserPrincipal;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class JwtProvider implements IJwtProvider{

    @Value("${app.jwt.secret}")
    private String JWT_SECRET;

    @Value("${app.jwt.expiration-in-ms}")
    private String JWT_EXPIRATION_IN_MS;

    public String generateToken(UserPrincipal auth){

        String authorities = auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining());

        return Jwts.builder()
                .setSubject(auth.getUsername())
                .claim("roles",authorities)
                .claim("userId",auth.getId())
                .setExpiration(new Date(System.currentTimeMillis()+JWT_EXPIRATION_IN_MS))
                .compact();
    }

    public Authentication getAuthentication(HttpServletRequest httpServletRequest){
        Claims claims = extractClaims(httpServletRequest);
        if(claims == null){
            return null;
        }
        String username = claims.getSubject();
        Long userId = claims.get("userId", Long.class);

        Set<GrantedAuthority> authorities = Arrays.stream(claims.get("roles").toString().split(",")).map(SecurityUtils::covertToAuthority).collect(Collectors.toSet());

        UserPrincipal userBuild = UserPrincipal.builder()
                .username(username)
                .authorities(authorities)
                .id(userId)
                .build();

        if(username == null){
            return null;
        }

        return new UsernamePasswordAuthenticationToken(userBuild,null,authorities);

    }

    public boolean validateToken(HttpServletRequest request){

        Claims claims = extractClaims(request);
        if(claims==null){
            return false;
        }

        if(claims.getExpiration().before(new Date())){
            return false;
        }
        return true;

    }



    private Claims extractClaims(HttpServletRequest httpServletRequest){
        String token = SecurityUtils.extractAuthTokenFromRequest(httpServletRequest);
        if(token == null){
            return null;
        }
        return  Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();
    }












}
