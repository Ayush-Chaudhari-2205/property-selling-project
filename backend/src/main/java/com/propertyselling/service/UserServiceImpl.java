package com.propertyselling.service;

import com.propertyselling.Entity.User;
import com.propertyselling.dao.UserEntityDao;
import com.propertyselling.dtos.ApiResponse;
import com.propertyselling.dtos.UserSignupDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserEntityDao userEntityDao;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public ApiResponse<User> addUser(UserSignupDTO dto) {
        if (userEntityDao.findByEmail(dto.getEmail()).isPresent()) {
            return new ApiResponse<>("Email already registered!", null);
        }

        if (userEntityDao.findByMobileNumber(dto.getMobileNumber()).isPresent()) {
            return new ApiResponse<>("Mobile number already registered!", null);
        }
        User newUser = modelMapper.map(dto, User.class);
//        user.setPassword(encoder.encode(user.getPassword()));//pwd : encrypted using SHA
        newUser.setPassword(encoder.encode(dto.getPassword()));
        User savedUser = userEntityDao.save(newUser);
        return new ApiResponse<>("User registered successfully!", savedUser);
    }
}
