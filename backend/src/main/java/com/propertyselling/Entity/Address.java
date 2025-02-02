package com.propertyselling.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
//@Getter
//@Setter
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

    public String getAddressLine() {
        return addressLine;
    }

    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }
}
