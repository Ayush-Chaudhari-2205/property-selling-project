package com.propertyselling.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PropertyStatusUpdateDTO {

    @NotNull(message = "Property ID is required")
    private Long propertyId;

    @NotNull(message = "Admin ID is required")
    private Long adminId;

    @NotNull(message = "Property status must be specified")
    private Boolean isActive;
}
