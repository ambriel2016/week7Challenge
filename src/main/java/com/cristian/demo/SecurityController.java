package com.cristian.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class SecurityController {
    @Autowired
    private UserService userService;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/register")
    public String showRegistrationPage(Model model){
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String processRegistrationPage(@Valid @ModelAttribute("user") User user,
                                          BindingResult result,
                                          Model model) {
        model.addAttribute("user", user);
        if (result.hasErrors())
            return "register";
        else {
            userService.saveUser(user);
            model.addAttribute("message", "User Account Created.");
        }

        return "login";

    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }
}