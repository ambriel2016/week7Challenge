package com.cristian.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DataLoader implements CommandLineRunner {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    MessageRepository messageRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void run(String... strings) throws Exception{
        roleRepository.save(new Role("USER"));
        roleRepository.save(new Role("ADMIN"));
        Role adminRole = roleRepository.findByRole("ADMIN");
        Role userRole = roleRepository.findByRole("USER");

        User user = new User("jim@jim.com", "password", "Jim", "Jimmerson", true, "jim", "avatar1.png");
        user.setRoles(Arrays.asList(userRole));
        userRepository.save(user);

        User user1 = new User("cris@cris.com", "password", "Cris", "Reynoso", true, "cris", "avatar2.png");
        user.setRoles(Arrays.asList(userRole));
        userRepository.save(user1);

        User user2 = new User("rey@rey.com", "password", "Rey", "Aponte", true, "Rey", "avatar3.png");
        user.setRoles(Arrays.asList(userRole));
        userRepository.save(user);

        user = new User("admin@admin.com", "password", "Admin", "User", true, "admin", "avatar.png");
        user.setRoles(Arrays.asList(adminRole));
        userRepository.save(user);

        Message m= new Message(1,"1","1","1","1",

                "https://res.cloudinary.com/di5v2kyqq/image/upload/v1583418214/y7ediixk9eamiin7robr.jpg",user);
        messageRepository.save(m);
    }
}