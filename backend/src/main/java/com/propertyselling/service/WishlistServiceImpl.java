package com.propertyselling.service;

import com.propertyselling.Entity.Property;
import com.propertyselling.Entity.User;
import com.propertyselling.Entity.UserType;
import com.propertyselling.Entity.Wishlist;
import com.propertyselling.dao.PropertyEntityDao;
import com.propertyselling.dao.UserEntityDao;
import com.propertyselling.dao.WishlistEntityDao;
import com.propertyselling.dtos.ApiResponse;
import com.propertyselling.dtos.WishlistRequestDTO;
import com.propertyselling.dtos.WishlistResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class WishlistServiceImpl implements WishlistService {

    @Autowired
    private WishlistEntityDao wishlistEntityDao;

    @Autowired
    private PropertyEntityDao propertyEntityDao;

    @Autowired
    private UserEntityDao userEntityDao;

    @Override
    public ApiResponse<?> addToWishlist(WishlistRequestDTO dto) {
        Optional<User> buyerOpt = userEntityDao.findById(dto.getBuyerId());
        if (buyerOpt.isEmpty() || buyerOpt.get().getUserType() != UserType.BUYER) {
            return new ApiResponse<>("Invalid Buyer ID! Only buyers can add properties to wishlist.", null);
        }

        Optional<Property> propertyOpt = propertyEntityDao.findById(dto.getPropertyId());
        if (propertyOpt.isEmpty() || !propertyOpt.get().isActive()) {
            return new ApiResponse<>("Property not found or is inactive!", null);
        }

        // ✅ Check if the property is already in the wishlist
        if (wishlistEntityDao.existsByBuyerIdAndPropertyId(dto.getBuyerId(), dto.getPropertyId())) {
            return new ApiResponse<>("Property is already in the wishlist!", null);
        }

        Wishlist wishlist = new Wishlist();
        wishlist.setBuyer(buyerOpt.get());
        wishlist.setProperty(propertyOpt.get());

        wishlistEntityDao.save(wishlist);

        return new ApiResponse<>("Property added to wishlist successfully!", null);
    }

    @Override
    public ApiResponse<List<WishlistResponseDTO>> getWishlist(Long buyerId) {
        Optional<User> buyerOpt = userEntityDao.findById(buyerId);
        if (buyerOpt.isEmpty() || buyerOpt.get().getUserType() != UserType.BUYER) {
            return new ApiResponse<>("Invalid Buyer ID! Only buyers can view wishlist.", null);
        }

        List<Wishlist> wishlistItems = wishlistEntityDao.findByBuyerId(buyerId);

        if (wishlistItems.isEmpty()) {
            return new ApiResponse<>("No properties found in your wishlist!", null);
        }

        List<WishlistResponseDTO> wishlistDTOs = wishlistItems.stream()
                .map(wishlist -> {
                    Property property = wishlist.getProperty();
                    return new WishlistResponseDTO(
                            wishlist.getId(),
                            property.getId(),
                            property.getName(),
                            property.getPrice(),
                            property.getPropertyType().toString(),
                            property.isFurnished(),
                            property.getAddress().getCity(),
                            property.getAddress().getState(),
                            property.getAddress().getCountry()
                    );
                })
                .collect(Collectors.toList());

        return new ApiResponse<>("Wishlist retrieved successfully!", wishlistDTOs);
    }

    @Override
    public ApiResponse<?> removeFromWishlist(Long buyerId, Long propertyId) {
        Optional<User> buyerOpt = userEntityDao.findById(buyerId);
        if (buyerOpt.isEmpty() || buyerOpt.get().getUserType() != UserType.BUYER) {
            return new ApiResponse<>("Invalid Buyer ID! Only buyers can remove properties from wishlist.", null);
        }

        Optional<Wishlist> wishlistOpt = wishlistEntityDao.findByBuyerIdAndPropertyId(buyerId, propertyId);
        if (wishlistOpt.isEmpty()) {
            return new ApiResponse<>("Property not found in wishlist!", null);
        }

        wishlistEntityDao.delete(wishlistOpt.get());

        return new ApiResponse<>("Property removed from wishlist successfully!", null);
    }


}
