package com.nk.clothify_backend.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nk.clothify_backend.model.User;
import com.nk.clothify_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomeUserServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final ObjectMapper mapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = mapper.convertValue(userRepository.findByEmail(username),User.class);
        if (user == null) {
            throw new UsernameNotFoundException("user not found with email : "+username);
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),authorities);

    }
}
