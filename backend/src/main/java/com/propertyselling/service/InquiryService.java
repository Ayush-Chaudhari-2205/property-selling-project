package com.propertyselling.service;

import com.propertyselling.dtos.*;

import java.util.List;

public interface InquiryService {

    ApiResponse<?> submitInquiry(InquiryRequestDTO dto);

    ApiResponse<List<InquiryResponseDTO>> getInquiriesOnProperty(Long propertyId, Long sellerId);

    ApiResponse<?> respondToInquiry(InquiryResponseUpdateDTO dto);

    ApiResponse<List<BuyerInquiryDTO>> getInquiriesForSeller(Long sellerId);


}
