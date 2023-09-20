package com.university.userservice.repository;

import com.university.userservice.entity.UserType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTypeRepository extends JpaRepository<UserType, Integer> {
    UserType findByTypeName(String userType);
}
