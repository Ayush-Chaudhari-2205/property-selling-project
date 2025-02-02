package com.propertyguru.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AddressDTO {


    @NotBlank(message = "Address line is required")
    private String addressLine;

    @NotBlank(message = "City is required")
    @Size(max = 20, message = "City name should not exceed 20 characters")
    private String city;

    @NotBlank(message = "State is required")
    @Size(max = 20, message = "State name should not exceed 20 characters")
    private String state;

    @NotBlank(message = "Country is required")
    @Size(max = 20, message = "Country name should not exceed 20 characters")
    private String country;

    @NotBlank(message = "Pin code is required")
    @Pattern(regexp = "^[0-9]{6}$", message = "Pin code must be exactly 6 digits")
    private String pinCode;
}

