package com.cristian.demo;

import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class HomeController {
    @Autowired
    private UserService userService;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    CloudinaryConfig cloudc;

    @RequestMapping("/")
    public String homepage(Model model) {
        model.addAttribute("allmsg", messageRepository.findAll());

        User user = userService.getUser();
        if (user != null)
            model.addAttribute("loginuser", user.getUsername());
        else
            model.addAttribute("loginuser", "none");
        return "index1";

    }

    @RequestMapping("/about")
    public String about(){
        return "about";
    }

    @GetMapping("/addmessage")
    public String addMessage(Model model){
        model.addAttribute("message", new Message());
        return "addmessage";
    }

    @PostMapping("/processmessage")
    public String processCar(@ModelAttribute Message message,
                             @RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return "redirect:/addmessage";
        }
        try {
            Map uploadResult = cloudc.upload(file.getBytes(),
                    ObjectUtils.asMap("resourcetype", "auto"));
            message.setPhoto(uploadResult.get("url").toString());
            messageRepository.save(message);
        } catch (IOException e) {
            e.printStackTrace();
            return "redirect:/addmessage";
        }
        return "redirect:/";
    }

//    @RequestMapping("/processmessage")
//    public String processMessage(@Valid @ModelAttribute("message") Message message,
//                             BindingResult result, Model model){
//        if (result.hasErrors())
//            return "addmessage";
//        else {
//            DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
//            Calendar cal = Calendar.getInstance();
//            Date date = cal.getTime();
//            String todaysdate = dateFormat.format(date);
//            message.setPostedDate(todaysdate);
//
//            User currentUser = userService.getUser();
//            message.setPostedBy(currentUser.getUsername());
//            message.setUsers(Arrays.asList(currentUser));
//
//            messageRepository.save(message);
//            return "redirect:/";
//        }
//    }

    @RequestMapping("/detailmessage/{id}")
    public String showmessage(@PathVariable("id") long id, Model model){
        model.addAttribute("message", messageRepository.findById(id).get());
        return "showmessage";
    }

    @RequestMapping("/updatemessage/{id}")
    public String updateMsg(@PathVariable("id") long id, Model model){
        model.addAttribute("message", messageRepository.findById(id).get());
        return "messageform";
    }

    //    @RequestMapping("/processupdatemsg")
//    @PostMapping("/updateprocess")
//    public String processUpdateMessage(@Valid @ModelAttribute("message") Message message,
//                                   BindingResult result, Model model){
//        if (result.hasErrors())
//            return "updatemessage";
//        else {
//            DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
//            Calendar cal = Calendar.getInstance();
//            Date date = cal.getTime();
//            String todaysdate = dateFormat.format(date);
//            message.setPostedDate(todaysdate);
//
//            User currentUser = userService.getUser();
//            message.setPostedBy(currentUser.getUsername());
//            message.setUser(Arrays.asList(currentUser));
//
//            messageRepository.save(onemsg);
//            return "redirect:/";
//        }
//    }

    @RequestMapping("/deletemessage/{id}")
    public String deleteMsg(@PathVariable("id") long id, Model model) {
        messageRepository.deleteById(id);
        return "redirect:/";
    }

//    @RequestMapping("/search")
//    public String searchPerson(@RequestParam("search") String search, Model model){
//        model.addAttribute("messageSearch", messageRepository.findByTitle(search));
//        return "searchList";
//    }

    @RequestMapping("/admin")
    public String admin(Model model) {

        //pass currently logged-in user information to index.html
        User user = userService.getUser();
        if (user != null)
            model.addAttribute("user", user);

        return "admin";
    }

    @RequestMapping("/secure")
    public String secure(Principal principal, Model model) {
        String username=principal.getName();
        model.addAttribute("user", userRepository.findByUsername(username));
        return "secure";
    }
}