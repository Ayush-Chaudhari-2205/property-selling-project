package com.propertyguru.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "users")
@NoArgsConstructor
//@Getter
//@Setter
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public AadhaarCard getAadhaarCard() {
        return aadhaarCard;
    }

    public void setAadhaarCard(AadhaarCard aadhaarCard) {
        this.aadhaarCard = aadhaarCard;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}





