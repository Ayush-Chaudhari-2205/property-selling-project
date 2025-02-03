package com.propertyselling.dtos;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class WishlistResponseDTO {

    private Long wishlistId;
    private Long propertyId;
    private String propertyName;
    private Double price;
    private String propertyType;
    private boolean furnished;
    private String city;
    private String state;
    private String country;
}
