package com.propertyselling.service;

import com.propertyselling.Entity.User;
import com.propertyselling.dtos.*;

public interface UserService {
    public ApiResponse<User> addUser(UserSignupDTO dto);

    ApiResponse<UserProfileDTO> getUserProfile(Long userId);

    ApiResponse<?> addAadhaarCard(AadhaarRequestDTO dto);

    ApiResponse<?> addSecurityQuestion(SecurityQuestionDTO dto);

    ApiResponse<?> addOrUpdateAddress(Long userId, AddressDTO addressDTO);

    ApiResponse<?> updateUserProfile(Long userId, UserUpdateDTO dto);

    ApiResponse<Long> countUsersExcludingAdmins();

    ApiResponse<UserProfileDTO> getUserByEmail(String email);

    ApiResponse<?> updateUserByEmail(String email, UserUpdateDTO dto);
}
