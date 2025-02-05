package com.propertyselling.dao;

import com.propertyselling.Entity.Property;

import com.propertyselling.Entity.Property_Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PropertyEntityDao extends JpaRepository<Property,Long  >
{
    @Query("SELECT p FROM Property p WHERE p.isActive = true")
    List<Property> findAllActiveProperties();

    List<Property> findByNameContainingIgnoreCaseAndIsActiveTrue(String name);

    List<Property> findByPropertyTypeAndIsActiveTrue(Property_Type propertyType);

    List<Property> findByPriceBetweenAndIsActiveTrue(Double minPrice, Double maxPrice);

    List<Property> findByAddress_CityIgnoreCaseAndAddress_StateIgnoreCaseAndIsActiveTrue(String city, String state);


    List<Property> findByAddress_CityIgnoreCaseAndIsActiveTrue(String city);


    List<Property> findByAddress_StateIgnoreCaseAndIsActiveTrue(String state);

    List<Property> findByFurnishedAndIsActiveTrue(boolean furnished);

    // ✅ Fetch properties owned by a specific seller
    List<Property> findBySellerId(Long sellerId);

    // ✅ Count total active properties
    Long countByIsActiveTrue();
}
