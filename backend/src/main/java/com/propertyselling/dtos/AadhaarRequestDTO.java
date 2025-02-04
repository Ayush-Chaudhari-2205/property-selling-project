package com.propertyselling.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class AadhaarRequestDTO {

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Aadhaar Card Number is required")
    @Pattern(regexp = "^[2-9]{1}[0-9]{11}$", message = "Invalid Aadhaar Card Number")
    private String aadhaarNumber;
}
