package com.levi9.code9.usersservice.security;

import com.levi9.code9.usersservice.model.UserEntity;
import com.levi9.code9.usersservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> user = userRepository.findByUsername(username);
        user.orElseThrow(() -> new UsernameNotFoundException("Wrong login credentials"));
        return user.map(MyUserDetails::new).get();

    }
}
