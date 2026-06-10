package com.jane.service;

import com.jane.entity.JobPostActivity;
import com.jane.repository.JobPostActivityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JobActivityService {
    private final JobPostActivityRepository jobPostActivityRepository;

    public JobPostActivity save(JobPostActivity jobPostActivity) {
        return jobPostActivityRepository.save(jobPostActivity);
    }

}
