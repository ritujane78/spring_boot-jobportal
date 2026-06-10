package com.jane.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "recruiter_profile")
@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class RecruiterProfile {
    @Id
    private int userAccountId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_account_id")
    private Users userId;

    private String firstName;
    private String lastName;
    private String city;
    private String state;
    private String country;
    private String company;

    @Column(nullable = true, length = 64)
    private String profilePhoto;

    public RecruiterProfile(Users userId)   {
        this.userId = userId;
    }

    @Transient
    public String getPhotosImagePath(){
        if(profilePhoto == null)return null;
        return "photos/recruiter/" +userAccountId+"/"+profilePhoto;
    }
}
