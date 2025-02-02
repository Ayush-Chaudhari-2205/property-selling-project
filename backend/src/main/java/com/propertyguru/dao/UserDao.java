package com.propertyguru.dao;

import com.propertyguru.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User,Long> {

    boolean existsByEmail(String email);

    boolean existsByMobileNumber(String mobileNumber);

}
