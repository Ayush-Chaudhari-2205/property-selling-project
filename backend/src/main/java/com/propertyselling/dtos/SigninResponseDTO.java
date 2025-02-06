package com.propertyselling.dtos;

import com.propertyselling.Entity.User;
import com.propertyselling.Entity.UserType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SigninResponseDTO {
    private String jwt;
    private String email;
    private Long user_id;
    private String fullName;
    private String mobileNo;
    private UserType role;
//    "user_id": 8,
//            "email": "david@gmail.com",
//            "fullName": "David",
//            "mobileNo": "9876678998",
//            "role": "SELLER"


}
