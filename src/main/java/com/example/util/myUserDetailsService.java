package com.example.util;

import com.example.pojo.Authority;
import com.example.pojo.User;
import com.example.service.AuthorityService;
import com.example.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class myUserDetailsService implements UserDetailsService {
    private UserService userService;

    private AuthorityService authorityService;

    public myUserDetailsService(UserService userService, AuthorityService authorityService) {
        this.userService = userService;
        this.authorityService = authorityService;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserDetails userDetails;
        User user = userService.findByUsername(s);
        userDetails = new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthorities(s));
        return userDetails;
    }


    private Collection<GrantedAuthority> getAuthorities(String s) {
        List<GrantedAuthority> auList = new ArrayList<>();
        List<Authority> byUsername = authorityService.findByUsername(s);
        for (Authority authority : byUsername) {
            auList.add(new SimpleGrantedAuthority(authority.getAuthority()));
        }
        return auList;
    }
}