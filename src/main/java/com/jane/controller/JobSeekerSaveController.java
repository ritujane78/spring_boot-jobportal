package com.jane.controller;

import com.jane.entity.JobPostActivity;
import com.jane.entity.JobSeekerProfile;
import com.jane.entity.JobSeekerSave;
import com.jane.entity.Users;
import com.jane.service.JobActivityService;
import com.jane.service.JobSeekerProfileService;
import com.jane.service.JobSeekerSaveService;
import com.jane.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class JobSeekerSaveController {
    private final UsersService  usersService;
    private final JobSeekerProfileService  jobSeekerProfileService;
    private final JobActivityService jobActivityService;
    private final JobSeekerSaveService jobSeekerSaveService;

    @PostMapping("/job-details/save/{id}")
    public String save(@PathVariable("id") int id) {
        JobSeekerSave jobSeekerSave = new JobSeekerSave();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            String username = authentication.getName();
            Users user = usersService.findByEmail(username);
            Optional<JobSeekerProfile> seekerProfile = jobSeekerProfileService.getOne(user.getUserId());
            JobPostActivity jobPostActivity = jobActivityService.getOne(id);
            if(seekerProfile.isPresent() && jobPostActivity != null) {
                jobSeekerSave.setJob(jobPostActivity);
                jobSeekerSave.setUserId(seekerProfile.get());
            } else {
                throw new RuntimeException("User not found");
            }
            jobSeekerSaveService.addNew(jobSeekerSave);
        }
        return "redirect:/dashboard";
    }

    @GetMapping("saved-jobs/")
    public String savedJobs(Model model) {
        List<JobPostActivity> jobPostActivityList = new ArrayList<>();
        Object currentUserProfile = usersService.getCurrentUserProfile();

        List<JobSeekerSave> jobSeekerSaveList = jobSeekerSaveService.getCandidatesJobs((JobSeekerProfile) currentUserProfile);
        for(JobSeekerSave jobSeekerSave : jobSeekerSaveList){
            jobPostActivityList.add(jobSeekerSave.getJob());
        }

        model.addAttribute("jobPost", jobPostActivityList);
        model.addAttribute("user", currentUserProfile);
        return "saved-jobs";
    }
}
