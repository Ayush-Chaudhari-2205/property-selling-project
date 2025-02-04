package com.propertyselling.controller;

import com.propertyselling.dtos.ApiResponse;
import com.propertyselling.dtos.ReviewRequestDTO;
import com.propertyselling.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/review")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/add")
    public ResponseEntity<?> addReview(@RequestBody ReviewRequestDTO dto) {
        return ResponseEntity.ok(reviewService.addReview(dto));
    }

    @GetMapping("/property/{propertyId}")
    public ResponseEntity<?> getReviewsForProperty(@PathVariable Long propertyId) {
        return ResponseEntity.ok(reviewService.getReviewsForProperty(propertyId));
    }
}
