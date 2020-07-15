package com.levi9.code9.usersservice.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import com.levi9.code9.usersservice.dto.AuthenticationRequest;
import com.levi9.code9.usersservice.dto.AuthenticationResponse;
import com.levi9.code9.usersservice.security.MyUserDetails;
import com.levi9.code9.usersservice.security.MyUserDetailsService;
import com.levi9.code9.usersservice.security.jwt.JwtUtil;
import com.levi9.code9.usersservice.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class UserController {

    private final AuthenticationManager authenticationManager;
    private final MyUserDetailsService myUserDetailsService;
    private final UserService userService;
    private final JwtUtil jwTokenUtil;

    public UserController(AuthenticationManager authenticationManager, MyUserDetailsService myUserDetailsService, UserService userService, JwtUtil jwTokenUtil) {
        this.authenticationManager = authenticationManager;
        this.myUserDetailsService = myUserDetailsService;
        this.userService = userService;
        this.jwTokenUtil = jwTokenUtil;
    }

    @PostMapping(path = "/sign-in", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthenticationResponse> login(@RequestBody final AuthenticationRequest authenticationRequest) throws Exception {
        final String username = authenticationRequest.getUsername();
        final String password = authenticationRequest.getPassword();
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        }catch (BadCredentialsException e){
            throw new Exception("Wrong login credentials", e);
        }
        final UserDetails userDetails = myUserDetailsService.loadUserByUsername(username);

        final String jwt = jwTokenUtil.generateToken((MyUserDetails) userDetails);
        return new ResponseEntity<>(new AuthenticationResponse(jwt), HttpStatus.OK);
    }

    @PostMapping(path = "/sign-up", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthenticationResponse> register(@RequestBody final AuthenticationRequest authenticationRequest){
        final AuthenticationResponse response = userService.addNewUser(authenticationRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(path = "/sign-out", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthenticationRequest> logout(){
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
