package com.propertyselling.service;

import com.propertyselling.Entity.Property;
import com.propertyselling.Entity.Review;
import com.propertyselling.Entity.User;
import com.propertyselling.Entity.UserType;
import com.propertyselling.dao.PropertyEntityDao;
import com.propertyselling.dao.ReviewEntityDao;
import com.propertyselling.dao.UserEntityDao;
import com.propertyselling.dtos.ApiResponse;
import com.propertyselling.dtos.ReviewRequestDTO;
import com.propertyselling.dtos.ReviewResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewEntityDao reviewEntityDao;

    @Autowired
    private PropertyEntityDao propertyEntityDao;

    @Autowired
    private UserEntityDao userEntityDao;

    @Override
    public ApiResponse<?> addReview(ReviewRequestDTO dto) {
        Optional<User> buyerOpt = userEntityDao.findById(dto.getBuyerId());
        if (buyerOpt.isEmpty() || buyerOpt.get().getUserType() != UserType.BUYER) {
            return new ApiResponse<>("Invalid Buyer ID! Only buyers can submit reviews.", null);
        }

        Optional<Property> propertyOpt = propertyEntityDao.findById(dto.getPropertyId());
        if (propertyOpt.isEmpty() || !propertyOpt.get().isActive()) {
            return new ApiResponse<>("Property not found or is inactive!", null);
        }

        // ✅ Check if the buyer has already reviewed this property
        if (reviewEntityDao.existsByBuyerIdAndPropertyId(dto.getBuyerId(), dto.getPropertyId())) {
            return new ApiResponse<>("You have already reviewed this property!", null);
        }

        Review review = new Review();
        review.setProperty(propertyOpt.get());
        review.setBuyer(buyerOpt.get());
        review.setRating(dto.getRating());
        review.setComment(dto.getComment());

        reviewEntityDao.save(review);

        return new ApiResponse<>("Review submitted successfully!", null);
    }

    @Override
    public ApiResponse<List<ReviewResponseDTO>> getReviewsForProperty(Long propertyId) {
        Optional<Property> propertyOpt = propertyEntityDao.findById(propertyId);
        if (propertyOpt.isEmpty() || !propertyOpt.get().isActive()) {
            return new ApiResponse<>("Property not found or is inactive!", null);
        }

        List<Review> reviews = reviewEntityDao.findByPropertyId(propertyId);

        if (reviews.isEmpty()) {
            return new ApiResponse<>("No reviews found for this property!", null);
        }

        List<ReviewResponseDTO> reviewDTOs = reviews.stream()
                .map(review -> new ReviewResponseDTO(
                        review.getId(),
                        review.getProperty().getId(),
                        review.getProperty().getName(),
                        review.getBuyer().getId(),
                        review.getBuyer().getFullName(),
                        review.getRating(),
                        review.getComment()
                ))
                .collect(Collectors.toList());

        return new ApiResponse<>("Reviews retrieved successfully!", reviewDTOs);
    }
}
