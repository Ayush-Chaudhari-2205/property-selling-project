package com.propertyselling.service;

import com.propertyselling.Entity.Property;
import com.propertyselling.Entity.PropertyImage;
import com.propertyselling.dao.PropertyEntityDao;
import com.propertyselling.dao.PropertyImageEntityDao;
import com.propertyselling.dtos.ApiResponse;
import com.propertyselling.dtos.PropertyImageResponseDTO;
import jakarta.annotation.PostConstruct;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class PropertyImageServiceImpl implements PropertyImageService {

    @Autowired
    private PropertyEntityDao propertyEntityDao;

    @Autowired
    private PropertyImageEntityDao propertyImageEntityDao;

    // Base URL for image retrieval
    private static final String imageBaseUrl = "http://localhost:8000/property-images/images/";
    @Value("${file.upload.location}") // field level DI , <property name n value />
    // ${file.upload.location} SpEL :Spring expr language
    private String uploadFolder;

    @PostConstruct
    public void init() throws IOException {
        // chk if folder exists --yes --continue
        File folder = new File(uploadFolder);
        if (folder.exists()) {
            System.out.println("folder exists alrdy !");
        } else {
            // no --create a folder
            folder.mkdir();
            System.out.println("created a folder !");
        }
    }

    @Override
    public ApiResponse<PropertyImageResponseDTO> uploadPropertyImage(Long propertyId, Long sellerId, MultipartFile imageFile) {
        Optional<Property> propertyOpt = propertyEntityDao.findById(propertyId);
        if (propertyOpt.isEmpty() || !propertyOpt.get().isActive()) {
            return new ApiResponse<>("Property not found or inactive!", null);
        }

        Property property = propertyOpt.get();
        if (!property.getSeller().getId().equals(sellerId)) {
            return new ApiResponse<>("Unauthorized to upload images for this property!", null);
        }

        try {
            String path = uploadFolder.concat(imageFile.getOriginalFilename());
            System.out.println(path);
            FileUtils.writeByteArrayToFile(new File(path), imageFile.getBytes());
            PropertyImage propertyImage = new PropertyImage();
            propertyImage.setProperty(property);
            propertyImage.setImageUrl(path); // Store image as byte[]
//
            PropertyImage savedImage = propertyImageEntityDao.save(propertyImage);
//            String imageUrl = imageBaseUrl + savedImage.getId(); // Generate accessible URL
            byte[] temp1 = null;
            if (path != null) {
                // path ---> File --> byte[]
                temp1= FileUtils.readFileToByteArray(new File(path));
                // OR from DB : return emp.getImage();
            }

            return new ApiResponse<>("Image uploaded successfully!", new PropertyImageResponseDTO(savedImage.getId(), propertyId, path,temp1));

        } catch (IOException e) {
            return new ApiResponse<>("Error saving image!", null);
        }
    }

    @Override
    public ApiResponse<List<PropertyImageResponseDTO>> getPropertyImages(Long propertyId) {
        Optional<Property> propertyOpt = propertyEntityDao.findById(propertyId);
        if (propertyOpt.isEmpty() || !propertyOpt.get().isActive()) {
            return new ApiResponse<>("Property not found or inactive!", null);
        }

        List<PropertyImage> images = propertyImageEntityDao.findByPropertyId(propertyId);
        if (images.isEmpty()) {
            return new ApiResponse<>("No images found for this property!", null);
        }

        List<PropertyImageResponseDTO> imageDTOs = images.stream()
                .map(image -> {
                    try {
                        return new PropertyImageResponseDTO(
                                image.getId(),
                                propertyId,
                                null,
                                FileUtils.readFileToByteArray(new File(image.getImageUrl()))
                        );
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());

        return new ApiResponse<>("Property images retrieved successfully!", imageDTOs);
    }

    @Override
    public ApiResponse<byte[]> getRawImageData(Long imageId) {
        Optional<PropertyImage> imageOpt = propertyImageEntityDao.findById(imageId);
        if (imageOpt.isEmpty()) {
            return new ApiResponse<>("Image not found!", null);
        }

        return new ApiResponse<>("Image retrieved successfully!", null);
    }

    @Override
    public ApiResponse<?> deletePropertyImage(Long imageId, Long sellerId) {
        Optional<PropertyImage> imageOpt = propertyImageEntityDao.findById(imageId);
        if (imageOpt.isEmpty()) {
            return new ApiResponse<>("Image not found!", null);
        }

        PropertyImage image = imageOpt.get();
        if (!image.getProperty().getSeller().getId().equals(sellerId)) {
            return new ApiResponse<>("Unauthorized to delete this image!", null);
        }

        propertyImageEntityDao.delete(image);
        return new ApiResponse<>("Image deleted successfully!", null);
    }
}
