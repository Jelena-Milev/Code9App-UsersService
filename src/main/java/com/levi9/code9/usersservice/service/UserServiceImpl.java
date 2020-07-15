package com.levi9.code9.usersservice.service;

import com.levi9.code9.usersservice.dto.AuthenticationRequest;
import com.levi9.code9.usersservice.dto.AuthenticationResponse;
import com.levi9.code9.usersservice.exception.UserAlreadyExistsException;
import com.levi9.code9.usersservice.model.UserEntity;
import com.levi9.code9.usersservice.repository.UserRepository;
import com.levi9.code9.usersservice.security.MyUserDetails;
import com.levi9.code9.usersservice.security.jwt.JwtUtil;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public UserServiceImpl(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public AuthenticationResponse addNewUser(AuthenticationRequest request) {
        Optional<UserEntity> userEntity = userRepository.findByUsername(request.getUsername());
        if(userEntity.isPresent()){
            throw new UserAlreadyExistsException("User already exists.");
        }
        UserEntity.UserEntityBuilder userEntityBuilder = UserEntity.builder();
        userEntityBuilder.username(request.getUsername())
                .password(request.getPassword())
                .active(true)
                .role("ROLE_USER");
        final UserEntity newUser = userEntityBuilder.build();
        final UserEntity savedUser = userRepository.save(newUser);
        final MyUserDetails newUserDetails = new MyUserDetails(savedUser);
        final String jwt = jwtUtil.generateToken(newUserDetails);
        return new AuthenticationResponse(jwt);
    }
}
