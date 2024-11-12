package com.nk.clothify_backend.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nk.clothify_backend.config.JwtProvider;
import com.nk.clothify_backend.entity.UserEntity;
import com.nk.clothify_backend.exception.UserException;
import com.nk.clothify_backend.model.User;
import com.nk.clothify_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService  {

    private final UserRepository userRepository;
    private final ObjectMapper mapper;
    private final JwtProvider jwtProvider;


    @Override
    public User findUserById(Long userId) throws UserException {

        Optional<UserEntity> userById = userRepository.findById(userId);
        if (userById.isPresent()) {
            return mapper.convertValue(userById.get(),User.class);
        }

        throw new UserException("user not found with ID: "+userId);
    }

    @Override
    public User findUserProfileByJwt(String jwt) throws UserException {
        String email = jwtProvider.getEmailFromToken(jwt);

        UserEntity userEntity = userRepository.findByEmail(email);

        if (userEntity==null){
            throw new UserException("user not found with email: "+email);
        }

        return mapper.convertValue(userEntity, User.class);
    }
}
