package com.jane.controller;

import com.jane.entity.*;
import com.jane.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class JobSeekerApplyController {
    private final JobActivityService jobActivityService;
    private final UsersService usersService;
    private final JobSeekerApplyService  jobSeekerApplyService;
    private final JobSeekerSaveService  jobSeekerSaveService;
    private final RecruiterProfileService recruiterProfileService;
    private final JobSeekerProfileService jobSeekerProfileService;

    @GetMapping("job-details-apply/{id}")
    public String display(@PathVariable("id") Integer id, Model model){
        JobPostActivity jobDetails = jobActivityService.getOne(id);
        List<JobSeekerApply> jobSeekerApplyList = jobSeekerApplyService.getjobCandidates(jobDetails);
        List<JobSeekerSave> jobSeekerSaveList = jobSeekerSaveService.getjobCandidates(jobDetails);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("Recruiter"))){
                RecruiterProfile user = recruiterProfileService.getCurrentRecruiterProfile();
                if(user != null){
                    model.addAttribute("applyList", jobSeekerApplyList);
                }
            } else {
                    JobSeekerProfile user = jobSeekerProfileService.getCurrentJobSeekerProfile();
                    if(user != null){
                        boolean exists = false;
                        boolean saved = false;
                        for(JobSeekerApply jobSeekerApply : jobSeekerApplyList){
                            if(jobSeekerApply.getUserId().getUserAccountId() == user.getUserAccountId()){
                                exists = true;
                                break;
                            }
                        }
                        for(JobSeekerSave jobSeekerSave : jobSeekerSaveList){
                            if(jobSeekerSave.getUserId().getUserAccountId() == user.getUserAccountId()){
                                saved = true;
                                break;
                            }
                        }
                        model.addAttribute("alreadyApplied", exists);
                        model.addAttribute("alreadySaved", saved);
                    }
            }

        }
        JobSeekerApply jobSeekerApply = new JobSeekerApply();
        model.addAttribute("applyJob", jobSeekerApply);

        model.addAttribute("jobDetails", jobDetails);
        model.addAttribute("user", usersService.getCurrentUserProfile());
//        System.out.println(usersService.getCurrentUserProfile());

      return "job-details";
    }

    @PostMapping("dashboard/edit/{id}")
    public String editJob(@PathVariable("id") Integer id, Model model){
        JobPostActivity jobDetails = jobActivityService.getOne(id);
        model.addAttribute("jobPostActivity", jobDetails);
        model.addAttribute("user", usersService.getCurrentUserProfile());
        return "add-jobs";
    }

    @PostMapping("job-details/apply/{id}")
    public String applyJob(@PathVariable("id") Integer id, JobSeekerApply jobSeekerApply){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            String username = authentication.getName();
            Users user = usersService.findByEmail(username);
            Optional<JobSeekerProfile> seekerProfile = jobSeekerProfileService.getOne(user.getUserId());
            JobPostActivity jobPostActivity = jobActivityService.getOne(id);
            if(seekerProfile.isPresent() && jobPostActivity != null){
                jobSeekerApply = new JobSeekerApply();
                jobSeekerApply.setApplyDate(new Date(System.currentTimeMillis()));
                jobSeekerApply.setUserId(seekerProfile.get());
                jobSeekerApply.setJob(jobPostActivity);
            }else {
                throw new RuntimeException("User not found with email: " + username);
            }
            jobSeekerApplyService.addNew(jobSeekerApply);
        }
        return "redirect:/dashboard";
    }

}
