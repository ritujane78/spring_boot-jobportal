package com.jane.repository;

import com.jane.entity.JobPostActivity;
import com.jane.entity.JobSeekerProfile;
import com.jane.entity.JobSeekerSave;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobSeekerSaveRepository extends JpaRepository<JobSeekerSave, Integer> {
    List<JobSeekerSave> findByUserId(JobSeekerProfile userAccountId);

    List<JobSeekerSave> findByJob(JobPostActivity job);
}
