package com.propertyselling.dao;

import com.propertyselling.Entity.Inquiry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InquiryEntityDao extends JpaRepository<Inquiry, Long> {


    List<Inquiry> findByPropertyId(Long propertyId);

    List<Inquiry> findByBuyerId(Long buyerId);

    @Query("SELECT i FROM Inquiry i WHERE i.property.seller.id = :sellerId")
    List<Inquiry> findByPropertySellerId(@Param("sellerId") Long sellerId);
}
