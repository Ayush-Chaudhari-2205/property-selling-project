package com.propertyselling.dao;

import com.propertyselling.Entity.PropertyImage;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PropertyImageEntityDao extends JpaRepository<PropertyImage, Long> {

    // ✅ Fetch images by property ID
    List<PropertyImage> findByPropertyId(Long propertyId);

}
