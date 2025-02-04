package com.propertyselling.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserUpdateDTO {

    @NotBlank(message = "Full name is required")
    private String fullName;

    @Email(message = "Invalid email format")
    private String email;

    @Pattern(regexp = "^[6789]\\d{9}$", message = "Invalid mobile number")
    private String mobileNumber;

    private String question;  // Security question (optional)
    private String answer;  // Security answer (optional)

    @Pattern(regexp = "^[2-9]{1}[0-9]{11}$", message = "Invalid Aadhaar Card Number")
    private String aadhaarNumber;  // Aadhaar number (optional)

    private AddressDTO address;  // Address details (optional)
}
