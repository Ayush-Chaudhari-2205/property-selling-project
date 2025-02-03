package com.propertyselling.service;

import com.propertyselling.dtos.ApiResponse;
import com.propertyselling.dtos.PropertyImageResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PropertyImageService {
    ApiResponse<?> uploadPropertyImage(Long propertyId, Long sellerId, MultipartFile imageFile);

    ApiResponse<List<PropertyImageResponseDTO>> getPropertyImages(Long propertyId);

    ApiResponse<?> deletePropertyImage(Long imageId, Long sellerId);
}
