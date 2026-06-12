package com.jane.service;

import com.jane.entity.JobPostActivity;
import com.jane.entity.JobSeekerApply;
import com.jane.entity.JobSeekerProfile;
import com.jane.entity.Users;
import com.jane.repository.JobSeekerApplyRepository;
import com.jane.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JobSeekerApplyService {
    private final JobSeekerApplyRepository  jobSeekerApplyRepository;
    private final UsersRepository usersRepository;

    public List<JobSeekerApply> getCandidatesJobs(JobSeekerProfile userAccountId) {
        return jobSeekerApplyRepository.findByJobSeekerProfileId(userAccountId);
    }

    public List<JobSeekerApply> getjobCandidates(JobPostActivity job) {
        return jobSeekerApplyRepository.findByJob(job);
    }


    public void addNew(JobSeekerApply jobSeekerApply) {
        jobSeekerApplyRepository.save(jobSeekerApply);
    }
}
