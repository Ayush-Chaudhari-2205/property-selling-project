package com.propertyselling.dtos;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PropertyImageResponseDTO {

    private Long imageId;
    private Long propertyId;
    private String imageUrl;
    private byte[] image;
}
