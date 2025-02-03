package com.propertyselling.dao;

import com.propertyselling.Entity.Inquiry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InquiryEntityDao extends JpaRepository<Inquiry, Long> {


    List<Inquiry> findByPropertyId(Long propertyId);

    List<Inquiry> findByBuyerId(Long buyerId);
}
