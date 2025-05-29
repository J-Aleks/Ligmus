package com.example.ligmus.security.auth;

import com.example.ligmus.data.users.Admin;
import com.example.ligmus.data.users.Teacher;
import com.example.ligmus.data.users.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


public class CustomUserDetails implements UserDetails {

    private final User user;


    public CustomUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if ( this.user instanceof Admin)
            return List.of( new SimpleGrantedAuthority("ROLE_ADMIN") );
        if ( this.user instanceof Teacher)
            return List.of( new SimpleGrantedAuthority("ROLE_TEACHER") );
        return List.of( new SimpleGrantedAuthority("ROLE_STUDENT") );
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getUsername();
    }


}
