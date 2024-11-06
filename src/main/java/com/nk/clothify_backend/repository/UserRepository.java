package com.nk.clothify_backend.repository;

import com.nk.clothify_backend.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity,Long> {

    public UserEntity findByEmail (String email);
}
