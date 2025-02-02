package com.propertyselling.dtos;

import com.propertyselling.Entity.User;
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


}
