package com.jane.service;

import com.jane.entity.*;
import com.jane.repository.JobPostActivityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.chrono.JapaneseChronology;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class JobActivityService {
    private final JobPostActivityRepository jobPostActivityRepository;

    public JobPostActivity save(JobPostActivity jobPostActivity) {
        return jobPostActivityRepository.save(jobPostActivity);
    }
    public List<RecruiterJobsDto> getRecruiterJobs(int recruiter){
        List<RecruiterJobs> recruiterJobsDto = jobPostActivityRepository.getRecruiterJobs(recruiter);

        List<RecruiterJobsDto> recruiterJobsDtoList = new ArrayList<>();
        for (RecruiterJobs recruiterJob : recruiterJobsDto) {
            JobLocation loc = new JobLocation(recruiterJob.getLocationId(), recruiterJob.getCity(),
                    recruiterJob.getState(), recruiterJob.getCountry());
            JobCompany job = new JobCompany(recruiterJob.getCompanyId(), recruiterJob.getName(), "");
            recruiterJobsDtoList.add(new RecruiterJobsDto(recruiterJob.getTotalCandidates(), recruiterJob.getJobPostId(),
                    recruiterJob.getJobTitle(), loc, job));

        }
        return recruiterJobsDtoList;
    }

    public JobPostActivity getOne(Integer id) {
        return jobPostActivityRepository.findById(id).orElseThrow(() -> new
                RuntimeException("Job post activity not found with id: " + id));
    }

    public List<JobPostActivity> getAll() {
        return jobPostActivityRepository.findAll();
    }

    public List<JobPostActivity> search(String job, String location, List<String> type, List<String> remote, LocalDate searchDate) {
        return Objects.isNull(searchDate)? jobPostActivityRepository.searchWithoutDate(job, location, remote, type):
        jobPostActivityRepository.search(job, location, remote, type, searchDate);
    }
}
