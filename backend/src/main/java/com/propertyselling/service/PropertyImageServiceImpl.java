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

    private static final String UPLOAD_DIRECTORY = "D:/property-selling-project/uploads/property-images"; // ✅ Store absolute path

    @Override
    public ApiResponse<?> uploadPropertyImage(Long propertyId, Long sellerId, MultipartFile image) {
        Optional<Property> propertyOpt = propertyEntityDao.findById(propertyId);

        if (propertyOpt.isEmpty() || !propertyOpt.get().getSeller().getId().equals(sellerId)) {
            return new ApiResponse<>("Property not found or unauthorized!", null);
        }

        Property property = propertyOpt.get();

        try {
            // ✅ Ensure the upload directory exists
            File uploadDir = new File(UPLOAD_DIRECTORY);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            // ✅ Generate a unique filename
            String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
            Path filePath = Paths.get(UPLOAD_DIRECTORY, fileName);
            image.transferTo(filePath.toFile()); // ✅ Save the file to absolute path

            // ✅ Store the absolute file path in the database
            String absolutePath = filePath.toAbsolutePath().toString(); // ✅ Get absolute path

            PropertyImage propertyImage = new PropertyImage();
            propertyImage.setProperty(property);
            propertyImage.setImageUrl(absolutePath); // ✅ Save absolute path
            propertyImageEntityDao.save(propertyImage);

            return new ApiResponse<>("Image uploaded successfully!", absolutePath);
        } catch (IOException e) {
            return new ApiResponse<>("Error saving image file!", null);
        }
    }

    @Override
    public ApiResponse<List<String>> getPropertyImages(Long propertyId) {
        List<PropertyImage> images = propertyImageEntityDao.findByPropertyId(propertyId);

        if (images.isEmpty()) {
            return new ApiResponse<>("No images found for this property!", null);
        }

        List<String> imagePaths = images.stream()
                .map(PropertyImage::getImageUrl) // ✅ Returning absolute file paths
                .collect(Collectors.toList());

        return new ApiResponse<>("Property images retrieved successfully!", imagePaths);
    }

    @Override
    @Transactional
    public ApiResponse<?> deletePropertyImage(Long imageId, Long sellerId) {
        Optional<PropertyImage> imageOpt = propertyImageEntityDao.findById(imageId);

        if (imageOpt.isEmpty() || !imageOpt.get().getProperty().getSeller().getId().equals(sellerId)) {
            return new ApiResponse<>("Image not found or unauthorized!", null);
        }

        PropertyImage propertyImage = imageOpt.get();
        String filePath = propertyImage.getImageUrl(); // ✅ Get absolute file path

        // ✅ Delete file from disk
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }

        // ✅ Delete image record from database
        propertyImageEntityDao.delete(propertyImage);

        return new ApiResponse<>("Property image deleted successfully!", null);
    }
}
