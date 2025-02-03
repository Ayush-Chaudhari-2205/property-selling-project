package com.propertyselling.controller;

import com.propertyselling.dtos.ApiResponse;
import com.propertyselling.service.PropertyImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/property/image")
public class PropertyImageController {

    @Autowired
    private PropertyImageService propertyImageService;

    @PostMapping("/upload/{propertyId}/seller/{sellerId}")
    public ResponseEntity<?> uploadPropertyImage(
            @PathVariable Long propertyId,
            @PathVariable Long sellerId,
            @RequestParam("image") MultipartFile imageFile) {
        return ResponseEntity.ok(propertyImageService.uploadPropertyImage(propertyId, sellerId, imageFile));
    }

    @GetMapping("/{propertyId}")
    public ResponseEntity<?> getPropertyImages(@PathVariable Long propertyId) {
        return ResponseEntity.ok(propertyImageService.getPropertyImages(propertyId));
    }

    @DeleteMapping("/delete/{imageId}/seller/{sellerId}")
    public ResponseEntity<?> deletePropertyImage(@PathVariable Long imageId, @PathVariable Long sellerId) {
        return ResponseEntity.ok(propertyImageService.deletePropertyImage(imageId, sellerId));
    }
}
