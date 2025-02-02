package com.propertyguru.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
public class AadhaarCard extends BaseEntity {

    @Column(name = "card_no", length = 14, unique = true)
    private String cardNo;
}

