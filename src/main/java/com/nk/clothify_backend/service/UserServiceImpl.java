package com.nk.clothify_backend.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nk.clothify_backend.config.JwtProvider;
import com.nk.clothify_backend.entity.UserEntity;
import com.nk.clothify_backend.exception.UserException;
import com.nk.clothify_backend.model.User;
import com.nk.clothify_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

        return mapUserEntityToUser(userEntity);
    }


    public User mapUserEntityToUser(UserEntity entity) {
        if (entity == null) {
            return null;
        }

        User user = new User();
        user.setId(entity.getId());
        user.setFirstName(entity.getFirstName());
        user.setLastName(entity.getLastName());
        user.setPassword(entity.getPassword());
        user.setEmail(entity.getEmail());
        user.setRole(entity.getRole());
        user.setPhoneNumber(entity.getPhoneNumber());
        user.setCreatedAt(entity.getCreatedAt());

        // Map addressEntities
        if (entity.getAddressEntities() != null) {
            user.setAddressEntities(new ArrayList<>(entity.getAddressEntities()));
        }

        // Map paymentInfomation
        if (entity.getPaymentInfomation() != null) {
            user.setPaymentInfomation(new ArrayList<>(entity.getPaymentInfomation()));
        }

        // Map ratingEntities
        if (entity.getRatingEntities() != null) {
            user.setRatingEntities(new ArrayList<>(entity.getRatingEntities()));
        }

        // Map reviewEntities
        if (entity.getReviewEntities() != null) {
            user.setReviewEntities(new ArrayList<>(entity.getReviewEntities()));
        }

        return user;
    }

    public UserEntity mapUserToUserEntity(User user) {
        if (user == null) {
            return null;
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setId(user.getId());
        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());
        userEntity.setPassword(user.getPassword());
        userEntity.setEmail(user.getEmail());
        userEntity.setRole(user.getRole());
        userEntity.setPhoneNumber(user.getPhoneNumber());
        userEntity.setCreatedAt(user.getCreatedAt());

        // Map addressEntities
        if (user.getAddressEntities() != null) {
            userEntity.setAddressEntities(new ArrayList<>(user.getAddressEntities()));
        }

        // Map paymentInfomation
        if (user.getPaymentInfomation() != null) {
            userEntity.setPaymentInfomation(new ArrayList<>(user.getPaymentInfomation()));
        }

        // Map ratingEntities
        if (user.getRatingEntities() != null) {
            userEntity.setRatingEntities(new ArrayList<>(user.getRatingEntities()));
        }

        // Map reviewEntities
        if (user.getReviewEntities() != null) {
            userEntity.setReviewEntities(new ArrayList<>(user.getReviewEntities()));
        }

        return userEntity;
    }


}
