package com.propertyselling.dtos;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class InquiryResponseDTO {

    private Long inquiryId;
    private Long propertyId;
    private String propertyName;
    private Long buyerId;
    private String buyerName;
    private String message;
    private boolean responded;
}
