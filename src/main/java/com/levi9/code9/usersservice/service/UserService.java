package com.levi9.code9.usersservice.service;

import com.levi9.code9.usersservice.dto.AuthenticationRequest;
import com.levi9.code9.usersservice.dto.AuthenticationResponse;

public interface UserService {
    AuthenticationResponse addNewUser(AuthenticationRequest request);
}
