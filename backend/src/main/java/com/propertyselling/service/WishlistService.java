package com.propertyselling.service;

import com.propertyselling.dtos.ApiResponse;
import com.propertyselling.dtos.WishlistRequestDTO;
import com.propertyselling.dtos.WishlistResponseDTO;

import java.util.List;

public interface WishlistService {

    ApiResponse<?> addToWishlist(WishlistRequestDTO dto);

    ApiResponse<List<WishlistResponseDTO>> getWishlist(Long buyerId);

    ApiResponse<?> removeFromWishlist(Long buyerId, Long propertyId);


}
