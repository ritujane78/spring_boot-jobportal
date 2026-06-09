package com.jane.controller;

import com.jane.entity.Users;
import com.jane.entity.UsersType;
import com.jane.service.UsersService;
import com.jane.service.UsersTypeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UsersController {
    private final UsersTypeService usersTypeService;
    private final UsersService usersService;
    @GetMapping("/register")
    public String register(Model model) {
        List<UsersType> usersTypeList = usersTypeService.findAll();
        System.out.println(usersTypeList);
        model.addAttribute("getAllTypes", usersTypeList);
        model.addAttribute("user", new Users());
        return "register";
    }
    @PostMapping("/register/new")
    public String newRegister(@Valid Users user) {
//        System.out.println(user);
        usersService.saveUser(user);
        return "dashboard";
    }
}
