package com.jane.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"userId", "job"})
})
@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class JobSeekerApply implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer  id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userId", referencedColumnName = "user_account_id")
    private JobSeekerProfile userId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "job", referencedColumnName = "jobPostId")
    private JobPostActivity job;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date applyDate;

    private String coverLetter;


}
