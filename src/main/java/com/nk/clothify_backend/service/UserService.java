package com.nk.clothify_backend.service;


import com.nk.clothify_backend.entity.UserEntity;
import com.nk.clothify_backend.exception.UserException;
import com.nk.clothify_backend.model.User;

public interface UserService {

    public User findUserById(Long userId) throws UserException;

    public User findUserProfileByJwt(String jwt) throws UserException;
}
