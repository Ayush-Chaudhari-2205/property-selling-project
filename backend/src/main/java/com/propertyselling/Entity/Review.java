package com.propertyselling.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Entity
@Table(name = "reviews")
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
public class Review extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;

    @ManyToOne
    @JoinColumn(name = "buyer_id", nullable = false)
    private User buyer;

    @Column(nullable = false)
    private int rating;

    @Column(length = 500)
    private String comment;
}
