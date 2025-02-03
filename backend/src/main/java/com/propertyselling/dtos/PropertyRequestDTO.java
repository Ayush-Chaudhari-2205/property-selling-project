package com.propertyselling.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PropertyRequestDTO {

    @NotBlank(message = "Property name is required")
    private String name;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be greater than zero")
    private Double price;

    @NotNull(message = "Number of bedrooms is required")
    @Min(value = 1, message = "Bedrooms must be at least 1")
    private int bedrooms;

    @NotNull(message = "Number of bathrooms is required")
    @Min(value = 1, message = "Bathrooms must be at least 1")
    private int bathrooms;

    @NotNull(message = "Area is required")
    @Positive(message = "Area must be greater than zero")
    private double area;

    private boolean furnished;
    private boolean parkingAvailable;

    @NotBlank(message = "Property type is required")
    private String propertyType;

    @NotNull(message = "Seller ID is required")
    private Long sellerId;

    private String addressLine;
    private String city;
    private String state;
    private String country;
    private String pinCode;
}
