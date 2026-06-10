package com.jane.controller;

import com.jane.entity.JobPostActivity;
import com.jane.entity.Users;
import com.jane.repository.UsersRepository;
import com.jane.service.JobActivityService;
import com.jane.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.sql.Date;

@Controller
@RequiredArgsConstructor
public class JobPostActivityController {
    private final UsersService usersService;
    private final JobActivityService jobActivityService;

    @GetMapping("/dashboard")
    public String searchJobs(Model model) {
        Object currentUserProfile = usersService.getCurrentUserProfile();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            String username = authentication.getName();
            model.addAttribute("username", username);
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
