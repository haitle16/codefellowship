package com.haile.codefellowship;


import com.haile.codefellowship.models.ApplicationUser;
import com.haile.codefellowship.models.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;

@Controller
public class HomeController {

    // pull in the repo to create a newUser to save to the database.
    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @Autowired
    PasswordEncoder encoder;

    @GetMapping("/")
    public String getHome(Principal p) {
        if(p != null) {
            System.out.println(p.getName());
        } else {
            System.out.println("nobody is logged in");
        }
        return "home";
    }

    @GetMapping("/signup")
    public String getSignup() {
        return "signup";
    }

    @PostMapping("/signup")
    public RedirectView signup(String username, String password, String firstName, String lastName, String email) {
        ApplicationUser newUser = new ApplicationUser(username, encoder.encode(password), firstName, lastName, email);
        applicationUserRepository.save(newUser);
        return new RedirectView("/");
    }

    @GetMapping("/login")
    public String getLogin() {
        return "login";
    }

}
