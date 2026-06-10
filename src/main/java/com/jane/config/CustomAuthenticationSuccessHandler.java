package com.jane.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        System.out.println("username : " + username +" is successfully logged in");

        boolean isJobSeeker = authentication.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals("Job Seeker"));
        boolean isRecruiter = authentication.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals("Recruiter"));

        if (isJobSeeker || isRecruiter) {
            response.sendRedirect("/dashboard");
        }


    }
}
