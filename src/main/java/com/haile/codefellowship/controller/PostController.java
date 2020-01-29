package com.haile.codefellowship.controller;

import com.haile.codefellowship.models.ApplicationUser;
import com.haile.codefellowship.models.ApplicationUserRepository;
import com.haile.codefellowship.models.Post;
import com.haile.codefellowship.models.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;

@Controller
public class PostController {

    @Autowired
    ApplicationUserRepository applicationUserRepository;
    @Autowired
    PostRepository postRepository;

    @PostMapping("/addpost") // maybe call it addposts
    public RedirectView createPost(String body, Model m, Principal p) {
        Timestamp createdAt = new Timestamp(System.currentTimeMillis());
//        Post post = new Post(body, createdAt.toString(), applicationUserRepository.findByUsername(p.getName()));
        Post post = new Post(body, createdAt.toString(), applicationUserRepository.findByUsername(p.getName()));
        postRepository.save(post);
        return new RedirectView("/profile");

    }

    @GetMapping("/addpost")
    public String getAddPostPage(Principal p, Model m) {
        m.addAttribute("username", p.getName());
        return "addpost";
    }



}
