package com.propertyselling.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class WishlistRequestDTO {

    @NotNull(message = "Buyer ID is required")
    private Long buyerId;

    @NotNull(message = "Property ID is required")
    private Long propertyId;
}
