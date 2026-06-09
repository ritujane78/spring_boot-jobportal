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

    public Users saveUser(Users user) {
        user.setActive(true);
        user.setRegistrationDate(new Date(System.currentTimeMillis()));
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
}
