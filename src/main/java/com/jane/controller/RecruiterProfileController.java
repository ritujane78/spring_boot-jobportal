package com.jane.controller;

import com.jane.entity.RecruiterProfile;
import com.jane.entity.Users;
import com.jane.repository.UsersRepository;
import com.jane.service.RecruiterProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/recruiter-profile")
@RequiredArgsConstructor
public class RecruiterProfileController {
    private final UsersRepository usersRepository;
    private final RecruiterProfileService recruiterProfileService;

    @GetMapping("/")
    public String recruiterProfile(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            String username = authentication.getName();
            Users user = usersRepository.findByEmail(username).orElseThrow(() -> new
                    UsernameNotFoundException("User not found with email: " + username));
            Optional<RecruiterProfile> recruiterProfile = recruiterProfileService.getOne(user.getUserId());
            if(recruiterProfile.isPresent()){
                model.addAttribute("profile", recruiterProfile.get());
            }

        }
            return "recruiter_profile";
    }
}
