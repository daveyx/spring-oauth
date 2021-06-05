package com.example.oauthdemo.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class MyUserDetails implements UserDetails {

    private final Long userId;
    private final String username;
    private final String password;
    private final List<GrantedAuthority> grantedAuthorityList;


    public MyUserDetails(final Long userId,
                         final String username,
                         final String password) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        grantedAuthorityList = new ArrayList<>();
    }

    public Long getId() {
        return userId;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorityList;
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

}
