package com.propertyguru.entity;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class Wishlist extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "buyer_id", nullable = false)
    private User buyer;

    @ManyToOne
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;

}
