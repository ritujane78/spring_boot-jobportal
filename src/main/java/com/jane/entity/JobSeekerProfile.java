package com.jane.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "job_seeker_profile")
@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class JobSeekerProfile {
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
    private String workAuthorization;
    private String employmentType;
    private String resume;

    @OneToMany(targetEntity = Skills.class, cascade = CascadeType.ALL, mappedBy = "jobSeekerProfile")
    List<Skills> skills;

    @Column(nullable = true, length = 64)
    private String profilePhoto;

    public JobSeekerProfile(Users userId) {
        this.userId = userId;
    }
}
