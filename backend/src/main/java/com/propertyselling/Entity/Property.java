package com.propertyselling.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.modelmapper.spi.PropertyType;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
public class Property extends BaseEntity {

    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 255, nullable = false)
    private String description;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private int bedrooms;

    @Column(nullable = false)
    private int bathrooms;

    @Column(nullable = false)
    private double area;

    @Column(nullable = false)
    private boolean furnished;

    @Column(nullable = false)
    private boolean parkingAvailable;

    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false)
    private PropertyType propertyType;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    @ManyToOne
    @JoinColumn(name = "seller_id", nullable = false)
    private User seller;

    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PropertyImage> images = new ArrayList<>();

}
