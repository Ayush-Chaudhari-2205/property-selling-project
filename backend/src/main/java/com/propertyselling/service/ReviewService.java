package com.propertyselling.service;

import com.propertyselling.dtos.ApiResponse;
import com.propertyselling.dtos.ReviewRequestDTO;
import com.propertyselling.dtos.ReviewResponseDTO;

import java.util.List;

public interface ReviewService {
    ApiResponse<?> addReview(ReviewRequestDTO dto);

    ApiResponse<List<ReviewResponseDTO>> getReviewsForProperty(Long propertyId);
}
