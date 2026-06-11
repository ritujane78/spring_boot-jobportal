package com.jane.controller;

import com.jane.entity.JobPostActivity;
import com.jane.service.JobActivityService;
import com.jane.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class JobSeekerApplyController {
    private final JobActivityService jobActivityService;
    private final UsersService usersService;

    @GetMapping("job-details-apply/{id}")
    public String display(@PathVariable("id") Integer id, Model model){
        JobPostActivity jobDetails = jobActivityService.getOne(id);
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

}
