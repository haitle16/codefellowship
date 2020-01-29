package com.haile.codefellowship.controller;


import com.haile.codefellowship.models.ApplicationUser;
import com.haile.codefellowship.models.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.sql.Date;
import java.util.ArrayList;

@Controller
public class HomeController {

    // pull in the repo to create a newUser to save to the database.
    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @Autowired
    PasswordEncoder encoder;

    @GetMapping("/")
    public String getHome(Principal p, Model m) {
        if(p != null) {
            System.out.println(p.getName()+" is logged in!");
            m.addAttribute("username", p.getName());
            ApplicationUser user = applicationUserRepository.findByUsername(p.getName());
            m.addAttribute("user", user);
        } else {
            System.out.println("nobody is logged in");
        }
        return "home";
    }

    @GetMapping("/viewposts")
    public String getProfile(Principal p, Model m){
        m.addAttribute("username", p.getName());
        ApplicationUser users = applicationUserRepository.findByUsername(p.getName());
        m.addAttribute("users", users);
        return "viewposts";
    }

}
