package com.game.tournament.gametournament.service;



import com.game.tournament.gametournament.model.AuthenticationResponse;
import com.game.tournament.gametournament.model.Role;
import com.game.tournament.gametournament.model.Token;
import com.game.tournament.gametournament.model.Users;
import com.game.tournament.gametournament.repository.TokenRepository;
import com.game.tournament.gametournament.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    private final TokenRepository tokenRepository;

    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository repository,
                                 PasswordEncoder passwordEncoder,
                                 JwtService jwtService,
                                 TokenRepository tokenRepository,
                                 AuthenticationManager authenticationManager) {
        this.userRepository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.tokenRepository = tokenRepository;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationResponse register(Users userrequest) {

        // check if user already exist. if exist than authenticate the user
        String username = userrequest.getUsername();
        if(username==null || username.equals("")){
            return new AuthenticationResponse(null, "Username cannot be blank");
        } else if (username.length() < 3) {
            return new AuthenticationResponse(null, "Username must be at least 3 characters");
        }

        String emailid = userrequest.getEmailid();
        if(emailid == null || emailid.equals("")){
            return new AuthenticationResponse(null, "Email Id cannot be blank");
        }
        Long mobileno = userrequest.getMobileno();
        if(mobileno == null || mobileno.equals("")){
            return new AuthenticationResponse(null, "Mobile No. cannot be blank");
        }

        if(userRepository.findByUsername(username).isPresent()) {
            return new AuthenticationResponse(null, "Username already exist");
        }

        if(userRepository.findAllByEmailid(emailid).size()>0) {
            return new AuthenticationResponse(null, "Email Id already exist");
        }

        if(userRepository.findAllByMobileno(mobileno).size()>0) {
            return new AuthenticationResponse(null, "Mobile No. already exist");
        }

        Users user = new Users();
        user.setFirstname(userrequest.getFirstname());
        user.setLastname(userrequest.getLastname());
        user.setUsername(username);
        user.setEmailid(emailid);
        user.setMobileno(mobileno);

        String password = userrequest.getPassword();
        if(password==null || password.equals("")){
            return new AuthenticationResponse(null, "Password cannot be blank");
        } else if (password.length() < 8) {
            return new AuthenticationResponse(null, "Password must be at least 8 characters");
        } else if (!password.matches(".*[^a-zA-Z0-9].*")) {
            return new AuthenticationResponse(null, "Password must contain at least one symbol");
        }
        user.setPassword(passwordEncoder.encode(userrequest.getPassword()));

        Role role = userrequest.getRole();
        if(role==null || role.equals("")){
            role=Role.USER;
        }
        user.setRole(role);

        user = userRepository.save(user);

        String jwt = jwtService.generateToken(user);

        saveUserToken(jwt, user);

        return new AuthenticationResponse(jwt, "Signup Successfully");

    }

    public AuthenticationResponse authenticate(Users request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        Users user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        String jwt = jwtService.generateToken(user);

        revokeAllTokenByUser(user);
        saveUserToken(jwt, user);

        return new AuthenticationResponse(jwt, "Login Successfully");

    }
    private void revokeAllTokenByUser(Users user) {
        List<Token> validTokens = tokenRepository.findAllTokensByUsers(user.getId());
        if(validTokens.isEmpty()) {
            return;
        }

        validTokens.forEach(t-> {
            t.setLoggedOut(true);
        });

        tokenRepository.saveAll(validTokens);
    }
    private void saveUserToken(String jwt, Users user) {
        Token token = new Token();
        token.setToken(jwt);
        token.setLoggedOut(false);
        token.setUsers(user);
        tokenRepository.save(token);
    }
}
