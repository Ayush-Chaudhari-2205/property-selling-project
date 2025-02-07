package com.propertyselling.dtos;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BuyerInquiryDTO {

    private Long inquiryId;
    private Long propertyId;
    private String propertyName;
    private String buyerName;
    private String buyerEmail;
    private String message;
    private Boolean responded;
    private LocalDateTime createdAt;
}
