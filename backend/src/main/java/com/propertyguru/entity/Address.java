package com.propertyguru.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
public class Address extends BaseEntity {

    @Column(name = "address_line")
    private String addressLine;

    @Column(length = 20)
    private String city;

    @Column(length = 20)
    private String state;

    @Column(length = 20)
    private String country;

    @Column(length = 7, name = "pin_code")
    private String pinCode;
}


