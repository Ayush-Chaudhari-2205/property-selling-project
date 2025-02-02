package com.propertyguru.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true, exclude = { "password" })
public class User extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(length = 15, name = "user_Type")
    private UserType userType;

    private String fullName;

    @Column(length = 160, unique = true)
    private String email;

    @Column(name = "mobile_number", length = 10, nullable = false)
    private String mobileNumber;

    @Column(length = 255, nullable = false)
    private String password;

    private String question;

    private String answer;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "aadhaar_card_id")
    private AadhaarCard aadhaarCard;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;
}





