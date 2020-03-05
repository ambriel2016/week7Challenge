package com.cristian.demo;

import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.Map;


@Controller
public class HomeController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    CloudinaryConfig cloudc;

    @RequestMapping("/")
    public String index(){
        return "index";
    }
    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/add")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        return "form";
    }

   @RequestMapping("/secure")

    public String secure(Principal principal, Model model){
        String username = principal.getName();
        model.addAttribute("user", userRepository.findByUsername(username));
        return "secure";
    }
    @GetMapping("/register")
    public String showRegistrationPage(Model model){
        model.addAttribute("user", new User());
        return "register";
    }
    @PostMapping("/register")
    public String processRegistrationPage(@ModelAttribute("user") User user, Model model,
           @RequestParam("file") MultipartFile file) {
            if (file.isEmpty()) {
                return "redirect:/register";
            }
            try {
                Map uploadResult = cloudc.upload(file.getBytes(),
                        ObjectUtils.asMap("resourcetype", "auto"));
                user.setPhoto(uploadResult.get("url").toString());
                userRepository.save(user);
            } catch (IOException e) {
                e.printStackTrace();
                return "redirect:/register";
            }
            return "redirect:/";
        }
 }

