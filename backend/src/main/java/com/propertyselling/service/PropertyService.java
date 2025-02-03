package com.propertyselling.service;

import com.propertyselling.Entity.Property;
import com.propertyselling.dtos.ApiResponse;
import com.propertyselling.dtos.PropertyRequestDTO;
import com.propertyselling.dtos.PropertyResponseDTO;
import com.propertyselling.dtos.PropertyUpdateDTO;

import java.util.List;

public interface PropertyService {

    ApiResponse<Property> addProperty(PropertyRequestDTO dto);

    ApiResponse<PropertyResponseDTO> getPropertyById(Long porpertyId);

    ApiResponse<List<PropertyResponseDTO>> getAllProperties();

    ApiResponse<PropertyResponseDTO> updateProperty(PropertyUpdateDTO dto);

    ApiResponse<?> softDeleteProperty(Long propertyId, Long sellerId);

    ApiResponse<List<PropertyResponseDTO>> searchPropertiesByName(String name);

    ApiResponse<List<PropertyResponseDTO>> filterPropertiesByType(String propertyType);
}
