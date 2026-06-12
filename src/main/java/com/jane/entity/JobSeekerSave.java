package com.jane.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"userId", "jobs"})
})
@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class JobSeekerSave {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "user_account_id")
    private JobSeekerProfile userId;

    @ManyToOne
    @JoinColumn(name = "job",referencedColumnName = "jobPostId")
    private JobPostActivity job;


}
