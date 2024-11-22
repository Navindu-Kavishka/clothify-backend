package com.nk.clothify_backend.service;


import com.nk.clothify_backend.entity.UserEntity;
import com.nk.clothify_backend.exception.UserException;
import com.nk.clothify_backend.model.User;

public interface UserService {

    User findUserById(Long userId) throws UserException;

    User findUserProfileByJwt(String jwt) throws UserException;

    User mapUserEntityToUser(UserEntity entity);

    UserEntity mapUserToUserEntity(User user);
}
