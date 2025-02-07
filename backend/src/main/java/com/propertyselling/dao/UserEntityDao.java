package com.propertyselling.dao;

import com.propertyselling.Entity.User;
import com.propertyselling.Entity.UserType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserEntityDao extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByMobileNumber(String mobileNumber);

    // ✅ Count total users excluding Admins
    Long countByUserTypeNot(UserType userType);

    // ✅ Fetch active users who are not Admins
    List<User> findByIsActiveFalseAndUserTypeNot(UserType userType);
}
