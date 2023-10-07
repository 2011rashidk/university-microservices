package com.university.userservice.repository;

import com.university.userservice.entity.User;
import com.university.userservice.entity.UserType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    Page<User> findAllByUserType(UserType userType, Pageable pageable);
}
