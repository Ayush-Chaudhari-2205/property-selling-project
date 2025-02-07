package com.propertyselling.dao;

import com.propertyselling.Entity.Property;

import com.propertyselling.Entity.Property_Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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




    @Query("SELECT p FROM Property p WHERE " +
            "(:type IS NULL OR p.propertyType = :type) " +
            "AND (:minPrice IS NULL OR p.price >= :minPrice) " +
            "AND (:maxPrice IS NULL OR p.price <= :maxPrice) " +
            "AND (:city IS NULL OR p.address.city LIKE %:city%) " +
            "AND (:state IS NULL OR p.address.state LIKE %:state%) " +
            "AND (:furnished IS NULL OR p.furnished = :furnished)")
    List<Property> filterProperties(
            @Param("type") String type,
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice,
            @Param("city") String city,
            @Param("state") String state,
            @Param("furnished") Boolean furnished
    );
}
