package com.game.tournament.gametournament.service;



import com.game.tournament.gametournament.model.AuthenticationResponse;
import com.game.tournament.gametournament.model.Role;
import com.game.tournament.gametournament.model.Token;
import com.game.tournament.gametournament.model.Users;
import com.game.tournament.gametournament.repository.TokenRepository;
import com.game.tournament.gametournament.repository.UserRepository;
import com.game.tournament.gametournament.utils.DataTypeUtility;
import com.game.tournament.gametournament.utils.ResponseDTOFactory;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.crypto.Data;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    private final TokenRepository tokenRepository;

    private final AuthenticationManager authenticationManager;

    @Autowired
    private ResponseDTOFactory ResponseDTOFactory;

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

    public ResponseEntity<?> register(Users userrequest) {

        String username = userrequest.getUsername();
        if(username==null || username.equals("")){
            return ResponseDTOFactory.failedMessage("Username cannot be blank");
        } else if (username.length() < 3) {
            return ResponseDTOFactory.failedMessage("Username must be at least 3 characters");
        }

        String mobileno = userrequest.getMobileno();
        if(mobileno == null || mobileno.equals("")){
            return ResponseDTOFactory.failedMessage("Mobile No. cannot be blank");
        }

        String emailid = userrequest.getEmailid();
        if(emailid == null || emailid.equals("")){
            return ResponseDTOFactory.failedMessage("Email Id cannot be blank");
        }

        String password = userrequest.getPassword();
        if(password==null || password.equals("")){
            return ResponseDTOFactory.failedMessage("Password cannot be blank");
        } else if (password.length() < 8) {
            return ResponseDTOFactory.failedMessage("Password must be at least 8 characters");
        } else if (!password.matches(".*[^a-zA-Z0-9].*")) {
            return ResponseDTOFactory.failedMessage("Password must contain at least one symbol");
        }

        if(userRepository.findByUsername(username).isPresent()) {
            return ResponseDTOFactory.failedMessage("Username already exist");
        }

        if(userRepository.findAllByEmailid(emailid).size()>0) {
            return ResponseDTOFactory.failedMessage("Email Id already exist");
        }

        if(userRepository.findAllByMobileno(mobileno).size()>0) {
            return ResponseDTOFactory.failedMessage("Mobile No. already exist");
        }

        Users user = new Users();
        user.setFirstname(userrequest.getFirstname());
        user.setLastname(userrequest.getLastname());
        user.setUsername(username);
        user.setEmailid(emailid);
        user.setMobileno(mobileno);

        user.setPassword(passwordEncoder.encode(userrequest.getPassword()));

        Role role = userrequest.getRole();
        if(role==null || role.equals("")){
            role=Role.USER;
        }
        user.setRole(role);

        user = userRepository.save(user);

        String jwt = jwtService.generateToken(user);

        saveUserToken(jwt, user);
        return ResponseDTOFactory.successMessage(jwt);
    }

    public ResponseEntity<?> authenticate(Users userrequest) {
        String username = userrequest.getUsername();
        if(username == null || username.equals("")){
            return ResponseDTOFactory.failedMessage("Username cannot be blank");
        }
        String password = userrequest.getPassword();
        if(password == null || password.equals("")){
            return ResponseDTOFactory.failedMessage("Password cannot be blank");
        }

        List<Users> user_list = userRepository.findAllByUsername(username);
        if(user_list == null || user_list.size()==0){
            return ResponseDTOFactory.failedMessage("Username is incorrect");
        }

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userrequest.getUsername(),
                            userrequest.getPassword()
                    )
            );
        } catch (UsernameNotFoundException e){
            e.printStackTrace();
            return ResponseDTOFactory.failedMessage("Bad Credential...");
        } catch (AuthenticationException e){
            e.printStackTrace();
            return ResponseDTOFactory.failedMessage("Bad Credential...");
        }

        Users user = user_list.get(0);
        String jwt = jwtService.generateToken(user);

        revokeAllTokenByUser(user);
        saveUserToken(jwt, user);
        return ResponseDTOFactory.successMessage(jwt);
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

    public ResponseEntity<?> forgetPassword(Users userrequest) {
        String username = userrequest.getUsername();
        String mobileno = userrequest.getMobileno();
        String emailid = userrequest.getEmailid();
        String newpassword = userrequest.getPassword();
        if(username == null || username.equals("")){
            return ResponseDTOFactory.failedMessage("Username cannot be blank");
        }
        if(mobileno == null || mobileno.equals("")){
            return ResponseDTOFactory.failedMessage("Mobile No. cannot be blank");
        }
        if(emailid == null || emailid.equals("")){
            return ResponseDTOFactory.failedMessage("Email Id cannot be blank");
        }
        List<Users> user_list = userRepository.findAllByUsername(username);
        if(user_list == null || user_list.size()==0){
            return ResponseDTOFactory.failedMessage("Username not matched");
        }
        Users user = user_list.get(0);
        if(!user.getMobileno().equals(mobileno)){
            return ResponseDTOFactory.failedMessage("Mobile No. not matched");
        }
        if(!user.getEmailid().equalsIgnoreCase(emailid)){
            return ResponseDTOFactory.failedMessage("Email Id not matched");
        }
        if(newpassword != null){
            if (newpassword.length() < 8){
                return ResponseDTOFactory.failedMessage("New Password must be at least 8 characters");
            } else if (!newpassword.matches(".*[^a-zA-Z0-9].*")) {
                return ResponseDTOFactory.failedMessage("New Password must contain at least one symbol");
            } else if (passwordEncoder.matches(newpassword,user.getPassword())) {
                return ResponseDTOFactory.failedMessage("New Password cannot be same to Old Password");
            }

            user.setPassword(passwordEncoder.encode(newpassword));
            userRepository.save(user);
            revokeAllTokenByUser(user);
        }

        return ResponseDTOFactory.successMessage("Successfully");
    }

    public ResponseEntity<?> tokenAuthenticate(Map<String ,Object> param, HttpServletRequest request) {
        String token = DataTypeUtility.stringValue(param.get("token"));
        String username = DataTypeUtility.stringValue(param.get("username"));

        if(token != null && !token.equals("")){
            boolean present = tokenRepository.findByToken(token).isPresent();
            if(present) {
                Token tokenmodal = tokenRepository.findByToken(token).get();
                boolean loggedOut = tokenmodal.isLoggedOut();
                Users users = tokenmodal.getUsers();
                String token_username = users.getUsername();
                if (username.equals(token_username)){
                    if (!loggedOut) {
                        boolean valid = jwtService.isValid(token, users);
                        if (valid) {
                                return ResponseDTOFactory.successMessage("Successfully Matched");
                        } else {
                            return ResponseDTOFactory.failedMessage("JWT Token Expire");
                        }
                    }
                 }
            }
        }

        return ResponseDTOFactory.failedMessage("Not Matched");
    }

    @Transactional
    public ResponseEntity<?> logout(Map<String ,Object> param, HttpServletRequest request) {
        Long userid = DataTypeUtility.longValue(param.get("userid"));
        boolean present = userRepository.findAllById(userid).isPresent();
        if(present){
            Long currentUserId = ResponseDTOFactory.getCurrentUserId(request);
            if(currentUserId.equals(userid)){
                List<Token> usertoken = tokenRepository.findAllTokensByUsers(userid);
                if(usertoken != null && usertoken.size()>0){
                    for (Token token : usertoken) {
                        token.setLoggedOut(true);
                        tokenRepository.save(token);
                    }
                    return ResponseDTOFactory.successMessage("Logout");
                }
            }
        }
        return ResponseDTOFactory.failedMessage("Cannot Logout");
    }

    public Object adminDetailsByUsername(Map<String, Object> param, HttpServletRequest request){
        Map<String,Object> resultMap = new HashMap<>();
        String username = DataTypeUtility.stringValue(param.get("username"));
        String currentUserName = ResponseDTOFactory.getCurrentUserName(request);
        if(username.equals(currentUserName)){
            Users usermodal = userRepository.findByUsername(username).get();
            resultMap.put("username",usermodal.getUsername());
            resultMap.put("emailid",usermodal.getEmailid());
            resultMap.put("mobileno",usermodal.getMobileno());
            resultMap.put("name",usermodal.getFirstname() + " " + usermodal.getLastname());
        }

        return resultMap;
    }
}
