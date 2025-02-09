package com.propertyselling.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
public class PropertyImage extends BaseEntity {

//    @Lob
//    @Column(name = "image_url", nullable = false, columnDefinition = "LONGBLOB")
//    private byte[] imageUrl; // URL or file path of the image


    @Column(name = "image_url", nullable = false, length = 2500)
    private String imageUrl; // URL or file path of the image

    @ManyToOne
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;
}
