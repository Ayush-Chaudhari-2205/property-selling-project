package com.propertyselling.dtos;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ReviewResponseDTO {

    private Long reviewId;
    private Long propertyId;
    private String propertyName;
    private Long buyerId;
    private String buyerName;
    private int rating;
    private String comment;
}
