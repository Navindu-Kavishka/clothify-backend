package com.nk.clothify_backend.service;


import com.nk.clothify_backend.entity.UserEntity;
import com.nk.clothify_backend.exception.UserException;

public interface UserService {

    public UserEntity findUserById(Long userId) throws UserException;

    public UserEntity findUserProfileByJwt(String jwt) throws UserException;
}
