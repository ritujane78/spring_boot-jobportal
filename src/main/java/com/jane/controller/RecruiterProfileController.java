package com.jane.controller;

import com.jane.entity.RecruiterProfile;
import com.jane.entity.Users;
import com.jane.repository.RecruiterProfileRepository;
import com.jane.repository.UsersRepository;
import com.jane.service.RecruiterProfileService;
import com.jane.util.FileUploadUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/recruiter-profile")
@RequiredArgsConstructor
public class RecruiterProfileController {
    private final UsersRepository usersRepository;
    private final RecruiterProfileService recruiterProfileService;
    private final RecruiterProfileRepository recruiterProfileRepository;

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

    @PostMapping("/addNew")
    public String addNew(RecruiterProfile recruiterProfile, @RequestParam("image") MultipartFile image,
                         Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            String username = authentication.getName();
            Users users = usersRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));
            recruiterProfile.setUserId(users);
            recruiterProfile.setUserAccountId(users.getUserId());
        }
        model.addAttribute("profile", recruiterProfile);
        String profilePhoto = "";
        if(!image.getOriginalFilename().equals("")){
            profilePhoto = StringUtils.cleanPath(Objects.requireNonNull(image.getOriginalFilename()));
            recruiterProfile.setProfilePhoto(profilePhoto);
        }
        RecruiterProfile savedProfile = recruiterProfileRepository.save(recruiterProfile);
        String uploadDir = "photos/recruiter/" + savedProfile.getUserAccountId();
        try {
            FileUploadUtil.saveFile(uploadDir, profilePhoto, image);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Implementation for adding a new recruiter profile
        return "redirect:/dashboard";
    }
}
