package com.propertyselling.dtos;

import com.propertyselling.Entity.AadhaarCard;
import com.propertyselling.Entity.Address;
import com.propertyselling.Entity.UserType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO extends BaseEntityDTO {

    private UserType userType;

    private String fullName;

     private String email;

     private String mobileNumber;


}
