package com.jane.controller;

import com.jane.entity.Users;
import com.jane.entity.UsersType;
import com.jane.service.UsersService;
import com.jane.service.UsersTypeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class UsersController {
    private final UsersTypeService usersTypeService;
    private final UsersService usersService;

    @GetMapping("/register")
    public String register(Model model) {
        List<UsersType> usersTypeList = usersTypeService.findAll();
        model.addAttribute("getAllTypes", usersTypeList);
        model.addAttribute("user", new Users());
        return "register";
    }
    @PostMapping("/register/new")
    public String newRegister(@Valid Users user, Model model) {
        Optional<Users> existingUser = usersService.getUserByEmail(user.getEmail());
        if(existingUser.isPresent()) {
            model.addAttribute("error", "The user already exists. Try registering a new one.");
            List<UsersType> usersTypeList = usersTypeService.findAll();
            model.addAttribute("getAllTypes", usersTypeList);
            model.addAttribute("user", new Users());
            return "register";
        }
//        System.out.println(user);
        usersService.saveUser(user);
        return "redirect:/dashboard";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication !=null){
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "redirect:/";
    }
}
