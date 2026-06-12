package com.jane.service;

import com.jane.entity.JobPostActivity;
import com.jane.entity.JobSeekerApply;
import com.jane.entity.JobSeekerProfile;
import com.jane.entity.JobSeekerSave;
import com.jane.repository.JobSeekerSaveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobSeekerSaveService {
    private final JobSeekerSaveRepository  jobSeekerSaveRepository;

    public List<JobSeekerSave> getCandidatesJobs(JobSeekerProfile userAccountId) {
        return jobSeekerSaveRepository.findByUserId(userAccountId);
    }

    public List<JobSeekerSave> getjobCandidates(JobPostActivity job) {
        return jobSeekerSaveRepository.findByJob(job);
    }
}
