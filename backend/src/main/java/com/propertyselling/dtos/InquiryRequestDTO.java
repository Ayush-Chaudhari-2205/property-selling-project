package com.propertyselling.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class InquiryRequestDTO {

    @NotNull(message = "Property ID is required")
    private Long propertyId;

    @NotNull(message = "Buyer ID is required")
    private Long buyerId;

    @NotBlank(message = "Message cannot be empty")
    private String message;
}
