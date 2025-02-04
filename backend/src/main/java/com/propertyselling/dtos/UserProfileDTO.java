package com.propertyselling.dtos;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserProfileDTO {

    private Long id;
    private String fullName;
    private String email;
    private String mobileNumber;
    private String userType; // BUYER, SELLER, ADMIN
    private String question; // Security question
    private String aadhaarNumber;
    private AddressDTO address;
}
