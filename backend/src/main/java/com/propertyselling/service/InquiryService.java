package com.propertyselling.service;

import com.propertyselling.dtos.ApiResponse;
import com.propertyselling.dtos.InquiryRequestDTO;
import com.propertyselling.dtos.InquiryResponseDTO;
import com.propertyselling.dtos.InquiryResponseUpdateDTO;

import java.util.List;

public interface InquiryService {

    ApiResponse<?> submitInquiry(InquiryRequestDTO dto);

    ApiResponse<List<InquiryResponseDTO>> getInquiriesOnProperty(Long propertyId, Long sellerId);

    ApiResponse<?> respondToInquiry(InquiryResponseUpdateDTO dto);


}
