package com.jane.service;

import com.jane.entity.JobPostActivity;
import com.jane.entity.JobSeekerApply;
import com.jane.entity.JobSeekerProfile;
import com.jane.repository.JobSeekerApplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobSeekerApplyService {
    private final JobSeekerApplyRepository  jobSeekerApplyRepository;

    public List<JobSeekerApply> getCandidatesJobs(JobSeekerProfile userAccountId) {
        return jobSeekerApplyRepository.findByJobSeekerProfileId(userAccountId);
    }

    public List<JobSeekerApply> getjobCandidates(JobPostActivity job) {
        return jobSeekerApplyRepository.findByJob(job);
    }
}
