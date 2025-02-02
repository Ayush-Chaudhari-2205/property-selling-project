package com.propertyguru.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
public class PropertyImage extends BaseEntity {
    @Column(nullable = false)
    private String imageUrl; // URL or file path of the image

    @ManyToOne
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;
}
