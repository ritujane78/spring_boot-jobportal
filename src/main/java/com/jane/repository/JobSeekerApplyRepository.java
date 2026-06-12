package com.jane.repository;

import com.jane.entity.JobPostActivity;
import com.jane.entity.JobSeekerApply;
import com.jane.entity.JobSeekerProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobSeekerApplyRepository extends JpaRepository<JobSeekerApply, Integer> {
    List<JobSeekerApply> findByUserId(JobSeekerProfile userAccountId);

    List<JobSeekerApply> findByJob(JobPostActivity job);
}
