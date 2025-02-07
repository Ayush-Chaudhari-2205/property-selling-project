package com.propertyselling.service;

import com.propertyselling.Entity.Property;
import com.propertyselling.dtos.*;

import java.util.List;

public interface PropertyService {

    ApiResponse<Property> addProperty(PropertyRequestDTO dto);

    ApiResponse<PropertyResponseDTO> getPropertyById(Long porpertyId);

    ApiResponse<List<PropertyResponseDTO>> getAllProperties();

    ApiResponse<PropertyResponseDTO> updateProperty(PropertyUpdateDTO dto);

    ApiResponse<?> softDeleteProperty(Long propertyId, Long sellerId);

    ApiResponse<List<PropertyResponseDTO>> searchPropertiesByName(String name);

    ApiResponse<List<PropertyResponseDTO>> filterPropertiesByType(String propertyType);

    ApiResponse<List<PropertyResponseDTO>> filterPropertiesByPriceRange(Double minPrice, Double maxPrice);

    ApiResponse<List<PropertyResponseDTO>> filterPropertiesByLocation(String city, String state);

    ApiResponse<List<PropertyResponseDTO>> filterPropertiesByFurnishingStatus(boolean furnished);

    ApiResponse<?> updatePropertyStatus(PropertyStatusUpdateDTO dto);

    ApiResponse<List<PropertyResponseDTO>> getPropertiesBySeller(Long sellerId);

    ApiResponse<Long> countActiveProperties();

    ApiResponse<List<PropertyResponseDTO>> filterProperties(String type, Double minPrice, Double maxPrice, String city, String state, Boolean furnished);

     ApiResponse<List<PropertyResponseDTO>> getAllActiveProperties();
}
