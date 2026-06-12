package com.jane.service;

import com.jane.entity.RecruiterProfile;
import com.jane.entity.Users;
import com.jane.repository.RecruiterProfileRepository;
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
public class RecruiterProfileService {
    private final RecruiterProfileRepository recruiterProfileRepository;
    private final UsersRepository usersRepository;

    public Optional<RecruiterProfile> getOne(Integer id){
        return recruiterProfileRepository.findById(id);
    }

    public RecruiterProfile getCurrentRecruiterProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            String username = authentication.getName();
            Users user = usersRepository.findByEmail(username).orElseThrow(() -> new
                    UsernameNotFoundException("User not found with email: " + username));

            Optional<RecruiterProfile> recruiterProfile = getOne(user.getUserId());
            return recruiterProfile.orElse(null);
        } else{
            return null;
        }
    }
}
