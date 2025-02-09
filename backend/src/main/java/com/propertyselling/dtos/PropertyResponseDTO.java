package com.propertyselling.dtos;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PropertyResponseDTO {

    private Long id;
    private String name;
    private String description;
    private Double price;
    private int bedrooms;
    private int bathrooms;
    private double area;
    private boolean furnished;
    private boolean parkingAvailable;
    private String propertyType;
    private String sellerName;
    private String sellerEmail;
    private Boolean isActive;
    private List<byte[]> imageUrls; // ✅ Avoids lazy loading issue

}
