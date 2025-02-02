package com.propertyselling.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor
//@Getter
//@Setter
@ToString(callSuper = true)
public class AadhaarCard extends BaseEntity {

    @Column(name = "card_no", length = 14, unique = true)
    private String cardNo;

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }
}