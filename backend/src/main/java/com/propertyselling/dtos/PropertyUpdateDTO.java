package com.propertyselling.dtos;

import jakarta.validation.constraints.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PropertyUpdateDTO {

    @NotNull(message = "Property ID is required")
    private Long id;

    @NotBlank(message = "Property name is required")
    private String name;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be greater than zero")
    private Double price;

    @NotNull(message = "Number of bedrooms is required")
    private int bedrooms;

    @NotNull(message = "Number of bathrooms is required")
    private int bathrooms;

    @NotNull(message = "Area is required")
    private double area;

    private boolean furnished;
    private boolean parkingAvailable;

    @NotBlank(message = "Property type is required")
    private String propertyType;

    @NotNull(message = "Seller ID is required") // Needed to validate authorization
    private Long sellerId;

    private String addressLine;
    private String city;
    private String state;
    private String country;
    private String pinCode;
}
