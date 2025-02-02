package com.propertyguru.service;

import com.propertyguru.dao.UserDao;
import com.propertyguru.dtos.ApiResponse;
import com.propertyguru.dtos.UserSignupDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public ApiResponse<?> addUser(UserSignupDto dto) {

    }
}
