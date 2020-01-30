package com.haile.codefellowship.models;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;
import java.util.List;

@Entity
public class ApplicationUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String username;
    String password;
    String firstName;
    String lastName;
    Date dateOfBirth;
    String bio;

    @OneToMany(mappedBy = "user")
    List<Post> posts;


    // https://www.baeldung.com/hibernate-many-to-many
    @ManyToMany
    @JoinTable(
            name = "follow_flow",
            joinColumns = { @JoinColumn(name = "follower_id") },
            inverseJoinColumns = { @JoinColumn(name = "following_id") }
    )
    List<ApplicationUser> usersIfollow;

    @ManyToMany(mappedBy = "usersIfollow")
    List<ApplicationUser> usersFollowedMe;


    public ApplicationUser() {}



    public ApplicationUser(String username, String password, String firstName, String lastName, Date dateOfBirth, String bio) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.bio = bio;
    }

    public List<Post> getPosts() {
        return this.posts;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public String getBio() {
        return bio;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Long getId() {
        return id;
    }

    public List<ApplicationUser> getUsersIfollow() {
        return usersIfollow;
    }

    public List<ApplicationUser> getUsersFollowedMe() {
        return usersFollowedMe;
    }
}
