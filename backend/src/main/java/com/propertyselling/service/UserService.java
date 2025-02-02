package com.propertyselling.service;

import com.propertyselling.Entity.User;
import com.propertyselling.dtos.ApiResponse;
import com.propertyselling.dtos.UserSignupDTO;

public interface UserService {
    public ApiResponse<User> addUser(UserSignupDTO dto);
}
