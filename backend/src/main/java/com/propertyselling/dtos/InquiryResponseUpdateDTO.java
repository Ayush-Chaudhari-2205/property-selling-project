package com.propertyselling.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class InquiryResponseUpdateDTO {

    @NotNull(message = "Inquiry ID is required")
    private Long inquiryId;

    @NotNull(message = "Seller ID is required")
    private Long sellerId;

    @NotNull(message = "Responded status must be specified")
    private Boolean responded;
}
