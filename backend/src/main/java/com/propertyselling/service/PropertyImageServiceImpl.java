package com.propertyselling.service;

import com.propertyselling.Entity.Property;
import com.propertyselling.Entity.PropertyImage;
import com.propertyselling.dao.PropertyEntityDao;
import com.propertyselling.dao.PropertyImageEntityDao;
import com.propertyselling.dtos.ApiResponse;
import com.propertyselling.dtos.PropertyImageResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
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

    private static final String IMAGE_DIRECTORY = "D:/property-images/";

    @Override
    public ApiResponse<?> uploadPropertyImage(Long propertyId, Long sellerId, MultipartFile imageFile) {
        Optional<Property> propertyOpt = propertyEntityDao.findById(propertyId);

        if (propertyOpt.isEmpty() || !propertyOpt.get().isActive()) {
            return new ApiResponse<>("Property not found or is inactive!", null);
        }

        Property property = propertyOpt.get();

        // ✅ Ensure the seller owns this property
        if (!property.getSeller().getId().equals(sellerId)) {
            return new ApiResponse<>("You are not authorized to upload images for this property!", null);
        }

        // ✅ Define fixed directory path (Modify as per your needs)
        String fixedDirectoryPath = "D:/property-images/";

        // ✅ Generate unique filename
        String fileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
        Path uploadPath = Paths.get(fixedDirectoryPath);

        try {
            // ✅ Ensure directory exists
            File directory = uploadPath.toFile();
            if (!directory.exists()) {
                boolean created = directory.mkdirs();
                if (!created) {
                    return new ApiResponse<>("Failed to create upload directory!", null);
                }
            }

            // ✅ Save file locally
            File imageFilePath = new File(directory, fileName);
            FileOutputStream outputStream = new FileOutputStream(imageFilePath);
            outputStream.write(imageFile.getBytes());
            outputStream.close();

            // ✅ Log file path
            System.out.println("Image saved successfully at: " + imageFilePath.getAbsolutePath());

            // ✅ Store image URL in the database
            String imageUrl = "/uploads/property-images/" + fileName;
            PropertyImage propertyImage = new PropertyImage();
            propertyImage.setProperty(property);
            propertyImage.setImageUrl(imageUrl);

            propertyImageEntityDao.save(propertyImage);

            return new ApiResponse<>("Image uploaded successfully!", imageUrl);
        } catch (IOException e) {
            e.printStackTrace();
            return new ApiResponse<>("Error saving image file!", null);
        }
    }

    @Override
    public ApiResponse<List<PropertyImageResponseDTO>> getPropertyImages(Long propertyId) {
        Optional<Property> propertyOpt = propertyEntityDao.findById(propertyId);
        if (propertyOpt.isEmpty() || !propertyOpt.get().isActive()) {
            return new ApiResponse<>("Property not found or is inactive!", null);
        }

        List<PropertyImage> images = propertyImageEntityDao.findByPropertyId(propertyId);

        if (images.isEmpty()) {
            return new ApiResponse<>("No images found for this property!", null);
        }

        List<PropertyImageResponseDTO> imageDTOs = images.stream()
                .map(image -> new PropertyImageResponseDTO(
                        image.getId(),
                        image.getProperty().getId(),
                        image.getImageUrl()
                ))
                .collect(Collectors.toList());

        return new ApiResponse<>("Property images retrieved successfully!", imageDTOs);
    }

    @Override
    public ApiResponse<?> deletePropertyImage(Long imageId, Long sellerId) {
        Optional<PropertyImage> imageOpt = propertyImageEntityDao.findById(imageId);
        if (imageOpt.isEmpty()) {
            return new ApiResponse<>("Image not found!", null);
        }

        PropertyImage image = imageOpt.get();
        Property property = image.getProperty();

        // ✅ Ensure the seller owns this property
        if (!property.getSeller().getId().equals(sellerId)) {
            return new ApiResponse<>("You are not authorized to delete this image!", null);
        }

        // ✅ Construct the full image file path
        String imageFileName = image.getImageUrl().replace("/uploads/property-images/", "");
        File imageFile = new File(IMAGE_DIRECTORY, imageFileName);

        // ✅ Delete the image file
        if (imageFile.exists()) {
            boolean deleted = imageFile.delete();
            if (!deleted) {
                return new ApiResponse<>("Failed to delete image file from the server!", null);
            }
        }

        // ✅ Remove image from the database
        propertyImageEntityDao.delete(image);

        return new ApiResponse<>("Image deleted successfully!", null);
    }
}
