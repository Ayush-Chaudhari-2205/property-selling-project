package com.propertyselling.dao;

import com.propertyselling.Entity.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WishlistEntityDao extends JpaRepository<Wishlist, Long> {

    // ✅ Check if a property is already in the buyer's wishlist
    boolean existsByBuyerIdAndPropertyId(Long buyerId, Long propertyId);

    List<Wishlist> findByBuyerId(Long buyerId);

    Optional<Wishlist> findByBuyerIdAndPropertyId(Long buyerId, Long propertyId);
}
