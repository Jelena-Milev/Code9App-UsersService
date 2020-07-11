package com.levi9.code9.usersservice.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import com.levi9.code9.usersservice.dto.UserDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
public class UserController {

    @PostMapping(path = "sign-up", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> register(@RequestBody final UserDto userDto){
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(path = "sign-in", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> login(@RequestBody final UserDto userDto){
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(path = "sign-out", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> logout(@RequestBody final UserDto userDto){
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
