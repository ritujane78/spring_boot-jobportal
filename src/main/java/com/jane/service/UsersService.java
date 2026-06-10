package com.jane.service;

import com.jane.entity.JobSeekerProfile;
import com.jane.entity.RecruiterProfile;
import com.jane.entity.Users;
import com.jane.repository.JobSeekerProfileRepository;
import com.jane.repository.RecruiterProfileRepository;
import com.jane.repository.UsersRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.apache.catalina.User;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Optional;

@Service
@Getter
@Setter
@RequiredArgsConstructor
public class UsersService {
    private final UsersRepository usersRepository;
    private final RecruiterProfileRepository recruiterProfileRepository;
    private final JobSeekerProfileRepository jobSeekerProfileRepository;
    private final PasswordEncoder passwordEncoder;

    public Users saveUser(Users user) {
        user.setActive(true);
        user.setRegistrationDate(new Date(System.currentTimeMillis()));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Users savedUser = usersRepository.save(user);
        int userTypeId = user.getUserTypeId().getUserTypeId();
        if (userTypeId == 1) {
            recruiterProfileRepository.save(new RecruiterProfile(savedUser));
        } else {
            jobSeekerProfileRepository.save(new JobSeekerProfile(savedUser));
        }
        return user;

    }
    public Optional<Users> getUserByEmail(String email) {
        return usersRepository.findByEmail(email);
    }

    public Object getCurrentUserProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)) {
            String username = authentication.getName();
            Users user = usersRepository.findByEmail(username).orElseThrow(() -> new
                    UsernameNotFoundException("User not found with email: " + username));
            int userId = user.getUserId();
            if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("Recruiter"))) {
                RecruiterProfile recruiterProfile = recruiterProfileRepository.findById(userId)
                        .orElseThrow(() -> new RuntimeException("Recruiter profile not found for user ID: " + userId));
                return recruiterProfile;
            }else {
                JobSeekerProfile jobSeekerProfile = jobSeekerProfileRepository.findById(userId)
                        .orElseThrow(() -> new RuntimeException("Job seeker profile not found for user ID: " + userId));
                return jobSeekerProfile;
            }
        }
            return null;
    }
    public Users getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)) {
            String username = authentication.getName();
            Users user = usersRepository.findByEmail(username).orElseThrow(() -> new
                    UsernameNotFoundException("User not found with email: " + username));
            return user;
        }
        return null;
    }
}
