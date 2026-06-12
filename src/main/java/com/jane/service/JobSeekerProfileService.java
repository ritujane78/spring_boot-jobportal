package com.jane.service;

import com.jane.entity.JobSeekerProfile;
import com.jane.entity.Users;
import com.jane.repository.JobSeekerProfileRepository;
import com.jane.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JobSeekerProfileService {
    private final JobSeekerProfileRepository jobSeekerProfileRepository;
    private final UsersRepository usersRepository;

    public Optional<JobSeekerProfile> getOne(Integer id){
        return jobSeekerProfileRepository.findById(id);

    }

    public JobSeekerProfile addNew(JobSeekerProfile jobSeekerProfile) {
        return jobSeekerProfileRepository.save(jobSeekerProfile);
    }

    public JobSeekerProfile getCurrentJobSeekerProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!( authentication instanceof AnonymousAuthenticationToken)) {
            String username = authentication.getName();
            Users user = usersRepository.findByEmail(username).orElseThrow(() -> new
                    UsernameNotFoundException("Username not found"));
            Optional<JobSeekerProfile> jobSeekerProfile = getOne(user.getUserId());
            return jobSeekerProfile.orElse(null);
        } else{
            return null;
        }
    }
}
