package com.propertyselling.service;

import com.propertyselling.Entity.*;
import com.propertyselling.dao.PropertyEntityDao;
import com.propertyselling.dao.UserEntityDao;
import com.propertyselling.dtos.ApiResponse;
import com.propertyselling.dtos.PropertyRequestDTO;
import com.propertyselling.dtos.PropertyResponseDTO;
import com.propertyselling.dtos.PropertyUpdateDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.propertyselling.Entity.Property_Type;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Transactional
public class PropertyServiceImpl implements PropertyService {

    @Autowired
    private PropertyEntityDao propertyEntityDao;

    @Autowired
    private UserEntityDao userEntityDao;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ApiResponse<Property> addProperty(PropertyRequestDTO dto) {
        Optional<User> sellerOpt = userEntityDao.findById(dto.getSellerId());

        if (sellerOpt.isEmpty() || sellerOpt.get().getUserType() != UserType.SELLER) {
            return new ApiResponse<>("Invalid Seller ID", null);
        }

        Property newProperty = modelMapper.map(dto, Property.class);
        newProperty.setSeller(sellerOpt.get());

        try {
            newProperty.setPropertyType(Property_Type.valueOf(dto.getPropertyType().toUpperCase())); // Convert to Enum
        } catch (IllegalArgumentException e) {
            return new ApiResponse<>("Invalid property type!", null);
        }

        Address address = new Address();
        address.setAddressLine(dto.getAddressLine());
        address.setCity(dto.getCity());
        address.setState(dto.getState());
        address.setCountry(dto.getCountry());
        address.setPinCode(dto.getPinCode());

        newProperty.setAddress(address);
        newProperty.setActive(true);

        propertyEntityDao.save(newProperty);
        return new ApiResponse<>("Property added successfully!", newProperty);
    }

    @Override
    public ApiResponse<PropertyResponseDTO> getPropertyById(Long porpertyId) {
        Optional<Property> propertyOpt = propertyEntityDao.findById(porpertyId);

        if (propertyOpt.isEmpty()) {
            return new ApiResponse<>("Property not found!", null);
        }

        Property property = propertyOpt.get();

        // ✅ Extract image URLs to avoid lazy loading issue
        List<String> imageUrls = property.getImages().stream()
                .map(PropertyImage::getImageUrl)
                .collect(Collectors.toList());

        // ✅ Map Property entity to PropertyResponseDTO using ModelMapper
        PropertyResponseDTO dto = modelMapper.map(property, PropertyResponseDTO.class);

        // ✅ Set additional fields manually
        dto.setSellerName(property.getSeller().getFullName());
        dto.setSellerEmail(property.getSeller().getEmail());
        dto.setImageUrls(imageUrls);

        return new ApiResponse<>("Property details fetched successfully!", dto);
    }

    @Override
    public ApiResponse<List<PropertyResponseDTO>> getAllProperties() {
        List<Property> properties = propertyEntityDao.findAll();

        if (properties.isEmpty()) {
            return new ApiResponse<>("No properties found!", null);
        }

        List<PropertyResponseDTO> propertyDTOList = properties.stream()
                .map(property -> {
                    PropertyResponseDTO dto = modelMapper.map(property, PropertyResponseDTO.class);
                    dto.setSellerName(property.getSeller().getFullName());
                    dto.setSellerEmail(property.getSeller().getEmail());

                    // ✅ Extract image URLs to avoid lazy loading issues
                    List<String> imageUrls = property.getImages().stream()
                            .map(PropertyImage::getImageUrl)
                            .collect(Collectors.toList());
                    dto.setImageUrls(imageUrls);

                    return dto;
                }).collect(Collectors.toList());

        return new ApiResponse<>("Properties fetched successfully!", propertyDTOList);
    }

    @Override
    public ApiResponse<PropertyResponseDTO> updateProperty(PropertyUpdateDTO dto) {
        Optional<Property> propertyOpt = propertyEntityDao.findById(dto.getId());

        if (propertyOpt.isEmpty()) {
            return new ApiResponse<>("Property not found!", null);
        }

        Property property = propertyOpt.get();

        // ✅ Validate that the seller updating the property is the owner
        if (!property.getSeller().getId().equals(dto.getSellerId())) {
            return new ApiResponse<>("You are not authorized to update this property!", null);
        }

        // ✅ Map updates from DTO to entity
        modelMapper.map(dto, property);

        // ✅ Convert String to Enum for PropertyType
        try {
            property.setPropertyType(Property_Type.valueOf(dto.getPropertyType().toUpperCase()));
        } catch (IllegalArgumentException e) {
            return new ApiResponse<>("Invalid property type!", null);
        }

        // ✅ Update Address
        Address address = property.getAddress();
        address.setAddressLine(dto.getAddressLine());
        address.setCity(dto.getCity());
        address.setState(dto.getState());
        address.setCountry(dto.getCountry());
        address.setPinCode(dto.getPinCode());

        // ✅ Save the updated property
        propertyEntityDao.save(property);

        // ✅ Convert to PropertyResponseDTO to avoid lazy loading issue
        PropertyResponseDTO responseDTO = modelMapper.map(property, PropertyResponseDTO.class);

        // ✅ Manually set the seller's information to avoid lazy loading issues
        if (property.getSeller() != null) {
            responseDTO.setSellerName(property.getSeller().getFullName());
            responseDTO.setSellerEmail(property.getSeller().getEmail());
        }

        // ✅ Fetch and map images (to prevent lazy loading issues)
        List<String> imageUrls = property.getImages().stream()
                .map(PropertyImage::getImageUrl)
                .toList();
        responseDTO.setImageUrls(imageUrls);

        return new ApiResponse<>("Property updated successfully!", responseDTO);
    }

    @Override
    public ApiResponse<?> softDeleteProperty(Long propertyId, Long sellerId) {
        Optional<Property> propertyOpt = propertyEntityDao.findById(propertyId);

        if (propertyOpt.isEmpty()) {
            return new ApiResponse<>("Property not found!", null);
        }

        Property property = propertyOpt.get();

        Optional<User> userOpt = userEntityDao.findById(sellerId);
        if (userOpt.isEmpty()) {
            return new ApiResponse<>("User not found!", null);
        }

        User user = userOpt.get();

        // ✅ Validate that the seller deleting the property is the owner OR an admin
        if (!property.getSeller().getId().equals(sellerId) && user.getUserType() != UserType.ADMIN) {
            return new ApiResponse<>("You are not authorized to delete this property!", null);
        }

        // ✅ Soft delete by setting isActive = false
        property.setActive(false);
        propertyEntityDao.save(property);

        return new ApiResponse<>("Property has been soft deleted successfully!", null);
    }

    @Override
    public ApiResponse<List<PropertyResponseDTO>> searchPropertiesByName(String name) {
        List<Property> properties = propertyEntityDao.findByNameContainingIgnoreCaseAndIsActiveTrue(name);

        if (properties.isEmpty()) {
            return new ApiResponse<>("No matching properties found!", null);
        }

        List<PropertyResponseDTO> propertyDTOList = properties.stream()
                .map(property -> {
                    PropertyResponseDTO dto = modelMapper.map(property, PropertyResponseDTO.class);
                    dto.setSellerName(property.getSeller().getFullName());
                    dto.setSellerEmail(property.getSeller().getEmail());

                    // ✅ Extract image URLs to avoid lazy loading issues
                    List<String> imageUrls = property.getImages().stream()
                            .map(PropertyImage::getImageUrl)
                            .collect(Collectors.toList());
                    dto.setImageUrls(imageUrls);

                    return dto;
                }).collect(Collectors.toList());

        return new ApiResponse<>("Matching properties found!", propertyDTOList);
    }

    @Override
    public ApiResponse<List<PropertyResponseDTO>> filterPropertiesByType(String propertyType) {
        // ✅ Validate the property type before querying
        Property_Type type;
        try {
            type = Property_Type.valueOf(propertyType.toUpperCase());
        } catch (IllegalArgumentException e) {
            return new ApiResponse<>("Invalid property type! Use HOUSE, APARTMENT, VILLA, COMMERCIAL, or LAND.", null);
        }

        List<Property> properties = propertyEntityDao.findByPropertyTypeAndIsActiveTrue(type);

        if (properties.isEmpty()) {
            return new ApiResponse<>("No properties found for the specified type!", null);
        }

        List<PropertyResponseDTO> propertyDTOList = properties.stream()
                .map(property -> {
                    PropertyResponseDTO dto = modelMapper.map(property, PropertyResponseDTO.class);
                    dto.setSellerName(property.getSeller().getFullName());
                    dto.setSellerEmail(property.getSeller().getEmail());

                    // ✅ Extract image URLs to avoid lazy loading issues
                    List<String> imageUrls = property.getImages().stream()
                            .map(PropertyImage::getImageUrl)
                            .collect(Collectors.toList());
                    dto.setImageUrls(imageUrls);

                    return dto;
                }).collect(Collectors.toList());

        return new ApiResponse<>("Properties filtered successfully!", propertyDTOList);
    }




}
