package com.haile.codefellowship.controller;

import com.haile.codefellowship.models.ApplicationUser;
import com.haile.codefellowship.models.ApplicationUserRepository;
import com.haile.codefellowship.models.Post;
import com.haile.codefellowship.models.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.sql.Timestamp;
import java.util.List;

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

    @DeleteMapping("/profile/{id}")
    public RedirectView deletePost(@PathVariable long id, Principal p) {
        ApplicationUser user = applicationUserRepository.findByUsername(p.getName());
        Post u = postRepository.getOne(id);
        if(user.getPosts().contains(u)) {
            postRepository.deleteById(id);
        }
        return new RedirectView("/profile");
    }

    @GetMapping("/viewposts")
    public String getAllPosts(Principal p, Model m){
        m.addAttribute("username", p.getName());
        List<Post> allPosts = postRepository.findAll();
        System.out.println("@@@@"+allPosts);
        m.addAttribute("allposts", allPosts);
//        ApplicationUser usersIfollow = applicationUserRepository.
        return "viewposts";
    }



}
