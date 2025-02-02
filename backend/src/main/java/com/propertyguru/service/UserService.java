package com.propertyguru.service;

import com.propertyguru.dao.UserDao;
import com.propertyguru.dtos.ApiResponse;
import com.propertyguru.dtos.UserSignupDto;
import org.springframework.beans.factory.annotation.Autowired;

public interface UserService {



    public ApiResponse<?> addUser();
}
