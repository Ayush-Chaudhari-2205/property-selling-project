package com.propertyselling.service;

import com.propertyselling.Entity.Inquiry;
import com.propertyselling.Entity.Property;
import com.propertyselling.Entity.User;
import com.propertyselling.Entity.UserType;
import com.propertyselling.dao.InquiryEntityDao;
import com.propertyselling.dao.PropertyEntityDao;
import com.propertyselling.dao.UserEntityDao;
import com.propertyselling.dtos.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class InquiryServiceImpl implements InquiryService {

    @Autowired
    private InquiryEntityDao inquiryEntityDao;

    @Autowired
    private PropertyEntityDao propertyEntityDao;

    @Autowired
    private UserEntityDao userEntityDao;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ApiResponse<?> submitInquiry(InquiryRequestDTO dto) {
        Optional<User> buyerOpt = userEntityDao.findById(dto.getBuyerId());
        if (buyerOpt.isEmpty() || buyerOpt.get().getUserType() != UserType.BUYER) {
            return new ApiResponse<>("Invalid Buyer ID! Only buyers can submit inquiries.", null);
        }

        Optional<Property> propertyOpt = propertyEntityDao.findById(dto.getPropertyId());
        if (propertyOpt.isEmpty() || !propertyOpt.get().isActive()) {
            return new ApiResponse<>("Property not found or is inactive!", null);
        }

        Property property = propertyOpt.get();
        Inquiry inquiry = new Inquiry();
        inquiry.setProperty(property);
        inquiry.setBuyer(buyerOpt.get());
        inquiry.setMessage(dto.getMessage());
        inquiry.setResponded(false);

        inquiryEntityDao.save(inquiry);

        return new ApiResponse<>("Inquiry submitted successfully!", null);
    }

    @Override
    public ApiResponse<List<InquiryResponseDTO>> getInquiriesOnProperty(Long propertyId, Long sellerId) {
        Optional<Property> propertyOpt = propertyEntityDao.findById(propertyId);

        if (propertyOpt.isEmpty() || !propertyOpt.get().isActive()) {
            return new ApiResponse<>("Property not found or is inactive!", null);
        }

        Property property = propertyOpt.get();

        // ✅ Ensure the seller owns this property
        if (!property.getSeller().getId().equals(sellerId)) {
            return new ApiResponse<>("You are not authorized to view inquiries on this property!", null);
        }

        List<Inquiry> inquiries = inquiryEntityDao.findByPropertyId(propertyId);

        if (inquiries.isEmpty()) {
            return new ApiResponse<>("No inquiries found for this property!", null);
        }

        List<InquiryResponseDTO> inquiryDTOs = inquiries.stream()
                .map(inquiry -> new InquiryResponseDTO(
                        inquiry.getId(),
                        property.getId(),
                        property.getName(),
                        inquiry.getBuyer().getId(),
                        inquiry.getBuyer().getFullName(),
                        inquiry.getMessage(),
                        inquiry.isResponded()
                ))
                .collect(Collectors.toList());

        return new ApiResponse<>("Inquiries retrieved successfully!", inquiryDTOs);
    }

    @Override
    public ApiResponse<?> respondToInquiry(InquiryResponseUpdateDTO dto) {
        Optional<Inquiry> inquiryOpt = inquiryEntityDao.findById(dto.getInquiryId());

        if (inquiryOpt.isEmpty()) {
            return new ApiResponse<>("Inquiry not found!", null);
        }

        Inquiry inquiry = inquiryOpt.get();
        Property property = inquiry.getProperty();

        // ✅ Ensure the seller owns this property
        if (!property.getSeller().getId().equals(dto.getSellerId())) {
            return new ApiResponse<>("You are not authorized to respond to this inquiry!", null);
        }

        // ✅ Update inquiry status
        inquiry.setResponded(dto.getResponded());
        inquiryEntityDao.save(inquiry);

        return new ApiResponse<>("Inquiry response updated successfully!", null);
    }

    @Override
    public ApiResponse<List<BuyerInquiryDTO>> getInquiriesForSeller(Long sellerId) {
        List<Inquiry> inquiries = inquiryEntityDao.findByPropertySellerId(sellerId);

        if (inquiries.isEmpty()) {
            return new ApiResponse<>("No inquiries found for this seller!", null);
        }

        List<BuyerInquiryDTO> inquiryDTOList = inquiries.stream()
                .map(inquiry -> {
                    BuyerInquiryDTO dto = new BuyerInquiryDTO();
                    dto.setInquiryId(inquiry.getId()); // ✅ Ensure `inquiryId` is set
                    dto.setPropertyId(inquiry.getProperty().getId());
                    dto.setPropertyName(inquiry.getProperty().getName());
                    dto.setBuyerName(inquiry.getBuyer().getFullName());
                    dto.setBuyerEmail(inquiry.getBuyer().getEmail());
                    dto.setMessage(inquiry.getMessage());
                    dto.setResponded(inquiry.isResponded());
                    dto.setCreatedAt(inquiry.getCreatedOn()); // ✅ Ensure `createdAt` is set
                    return dto;
                }).collect(Collectors.toList());

        return new ApiResponse<>("Inquiries retrieved successfully!", inquiryDTOList);
    }


}
