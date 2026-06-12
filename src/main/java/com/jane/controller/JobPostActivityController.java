package com.jane.controller;

import com.jane.entity.*;
import com.jane.repository.UsersRepository;
import com.jane.service.JobActivityService;
import com.jane.service.JobSeekerApplyService;
import com.jane.service.JobSeekerSaveService;
import com.jane.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class JobPostActivityController {
    private final UsersService usersService;
    private final JobActivityService jobActivityService;
    private final JobSeekerApplyService  jobSeekerApplyService;
    private final JobSeekerSaveService  jobSeekerSaveService;

    @GetMapping("/dashboard")
    public String searchJobs(Model model,
                             @RequestParam(value = "job", required = false) String job,
                             @RequestParam(value = "location", required = false) String location,
                             @RequestParam(value = "partTime", required = false) String partTime,
                             @RequestParam(value = "fullTime", required = false) String fullTime,
                             @RequestParam(value = "freelance", required = false) String freelance,
                             @RequestParam(value = "remoteOnly", required = false) String remoteOnly,
                             @RequestParam(value = "officeOnly", required = false) String officeOnly,
                             @RequestParam(value = "partialRemote", required = false) String partialRemote,
                             @RequestParam(value = "today", required = false) boolean today,
                             @RequestParam(value = "days7", required = false) boolean days7,
                             @RequestParam(value = "days30", required = false) boolean days30

    ) {

        model.addAttribute("partTime", Objects.equals(partTime, "Part-Time"));
        model.addAttribute("fullTime", Objects.equals(partTime, "Full-Time"));
        model.addAttribute("freelance", Objects.equals(partTime, "Freelance"));

        model.addAttribute("remoteOnly", Objects.equals(partTime, "Remote-Only"));
        model.addAttribute("officeOnly", Objects.equals(partTime, "Office-Only"));
        model.addAttribute("partialRemote", Objects.equals(partTime, "Partial-Remote"));

        model.addAttribute("today", today);
        model.addAttribute("days7", days7);
        model.addAttribute("days30", days30);

        model.addAttribute("job", job);
        model.addAttribute("location", location);

        LocalDate searchDate = null;
        List<JobPostActivity> jobPost = null;
        boolean dateSearchFlag = true;
        boolean remote = true;
        boolean type = true;

        if (days30) {
            searchDate = LocalDate.now().minusDays(30);
        } else if (days7) {
            searchDate = LocalDate.now().minusDays(7);
        } else if (today) {
            searchDate = LocalDate.now();
        } else {
            dateSearchFlag = false;
        }

        if (partTime == null && fullTime == null && freelance == null) {
            partTime = "Part-Time";
            fullTime = "Full-Time";
            freelance = "Freelance";
            remote = false;
        }

        if (officeOnly == null && remoteOnly == null && partialRemote == null) {
            officeOnly = "Office-Only";
            remoteOnly = "Remote-Only";
            partialRemote = "Partial-Remote";
            type = false;
        }
        if(!dateSearchFlag && !remote && !type && !StringUtils.hasText(job) && !StringUtils.hasText(location)) {
            jobPost = jobActivityService.getAll();
        }else{
            jobPost = jobActivityService.search(job, location, Arrays.asList(partTime, fullTime, freelance),
                    Arrays.asList(remoteOnly, officeOnly, partialRemote), searchDate);
        }
        Object currentUserProfile = usersService.getCurrentUserProfile();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)) {
            String username = authentication.getName();
            model.addAttribute("username", username);
            if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("Recruiter"))) {
                List<RecruiterJobsDto> recruiterJobs = jobActivityService.getRecruiterJobs(((RecruiterProfile) currentUserProfile).getUserAccountId());
                model.addAttribute("jobPost", recruiterJobs);
            } else {
                List<JobSeekerApply> jobSeekerApplyList = jobSeekerApplyService.getCandidatesJobs((JobSeekerProfile) currentUserProfile);
                List<JobSeekerSave> jobSeekerSaveList = jobSeekerSaveService.getCandidatesJobs((JobSeekerProfile) currentUserProfile);

                boolean exist;
                boolean saved;

                for(JobPostActivity jobActivity : jobPost) {
                    exist = false;
                    saved = false;
                    for(JobSeekerApply jobSeekerApply : jobSeekerApplyList) {
                        if(Objects.equals(jobActivity.getJobPostId(), jobSeekerApply.getJob().getJobPostId())){
                            jobActivity.setIsActive(true);
                            exist = true;
                            break;
                        }
                    }
                    for(JobSeekerSave jobSeekerSave : jobSeekerSaveList) {
                        if (Objects.equals(jobActivity.getJobPostId(), jobSeekerSave.getJob().getJobPostId())){
                            jobActivity.setIsSaved(true);
                            saved = true;
                            break;
                        }
                    }
                    if(!exist) {
                        jobActivity.setIsActive(false);
                    }
                    if(!saved) {
                        jobActivity.setIsSaved(false);
                    }
                    model.addAttribute("jobPost", jobPost);
                }
            }
        }
        model.addAttribute("user", currentUserProfile);
        return "dashboard";
    }

    @GetMapping("/dashboard/add")
    public String addJobs(Model model) {
        model.addAttribute("jobPostActivity", new JobPostActivity());
        model.addAttribute("user", usersService.getCurrentUserProfile());
        return "add-jobs";

    }

    @PostMapping("dashboard/addNew")
    public String addJobs(@ModelAttribute JobPostActivity jobPostActivity, Model model) {
        Users user = usersService.getCurrentUser();
        if(user != null){
            jobPostActivity.setPostedById(user);
        }
        jobPostActivity.setPostedDate(new Date(System.currentTimeMillis()));
        model.addAttribute("jobPostActivity", jobPostActivity);
        JobPostActivity saved = jobActivityService.save(jobPostActivity);
        return "redirect:/dashboard";
    }


}
