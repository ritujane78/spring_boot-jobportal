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
    private Integer userAccountId;

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

    @OneToMany(targetEntity = Skill.class, cascade = CascadeType.ALL, mappedBy = "jobSeekerProfile")
    List<Skill> skills;

    @Column(nullable = true, length = 64)
    private String profilePhoto;

    @Transient
    public String getPhotosImagePath(){
        if(profilePhoto == null || userAccountId == null)return null;
        return "photos/candidate/" +userAccountId+"/"+profilePhoto;
    }

    public JobSeekerProfile(Users userId) {
        this.userId = userId;
    }
}
