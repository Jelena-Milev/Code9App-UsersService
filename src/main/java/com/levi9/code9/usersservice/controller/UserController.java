package com.levi9.code9.usersservice.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import com.levi9.code9.usersservice.dto.AuthenticationRequest;
import com.levi9.code9.usersservice.dto.AuthenticationResponse;
import com.levi9.code9.usersservice.security.MyUserDetails;
import com.levi9.code9.usersservice.security.MyUserDetailsService;
import com.levi9.code9.usersservice.security.jwt.JwtUtil;
import jdk.nashorn.internal.objects.annotations.Getter;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private JwtUtil jwTokenUtil;

    @GetMapping(path = "/hello")
    public String hello(){
        return "Hello world";
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
    public ResponseEntity<AuthenticationRequest> register(@RequestBody final AuthenticationRequest authenticationRequest){
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(path = "/sign-out", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthenticationRequest> logout(@RequestBody final AuthenticationRequest authenticationRequest){
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
