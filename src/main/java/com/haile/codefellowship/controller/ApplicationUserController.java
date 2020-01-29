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
public class ApplicationUserController {

    // pull in the repo to create a newUser to save to the database.
    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @Autowired
    PasswordEncoder encoder;

    @GetMapping("/signup")
    public String getSignup() {
        return "signup";
    }

    @PostMapping("/signup")
    public RedirectView signup(String username, String password, String firstName, String lastName, Date dateOfBirth, String bio, Model m) {
        if(applicationUserRepository.findByUsername(username) == null){
            ApplicationUser newUser = new ApplicationUser(username, encoder.encode(password), firstName, lastName, dateOfBirth, bio);

            // auto log in
            Authentication authentication = new UsernamePasswordAuthenticationToken(newUser, null, new ArrayList<>());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            applicationUserRepository.save(newUser);
            return new RedirectView("/profile");
        }else {
            return new RedirectView("/signup/?taken=true"); // prevent user from creating the same username in DB
        }

    }

    @GetMapping("/login")
    public String getLogin(Model m, Principal p) {
//        ApplicationUser user = applicationUserRepository.findByUsername(p.getName());
//        m.addAttribute("user", user);
        return "login";
    }

    @GetMapping("/profile")
    public String getProfile(Principal p, Model m){
        m.addAttribute("username", p.getName());
        ApplicationUser user = applicationUserRepository.findByUsername(p.getName());
        m.addAttribute("user", user);
        return "profile";
    }

}
