package com.example.ligmus.security.auth;

import com.example.ligmus.data.Entities.SubjectEntity;
import com.example.ligmus.data.subjects.Subject;
import com.example.ligmus.data.users.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;


public class CustomUserDetails implements UserDetails {

    private final User user;


    public CustomUserDetails(UserEntity userEntity) {
        this.user = new User(userEntity.getId(), userEntity.getUserType(), userEntity.getUsername(), userEntity.getFirstName(),
                userEntity.getLastName(), userEntity.getDateOfBirth(),  userEntity.getPassword());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if ( this.user.getUserType() == UserType.ADMIN)
            return List.of( new SimpleGrantedAuthority("ROLE_ADMIN") );
        if ( this.user.getUserType() == UserType.TEACHER)
            return List.of( new SimpleGrantedAuthority("ROLE_TEACHER") );
        else
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

    public UserType getUserType() { return this.user.getUserType(); }

    public String getFirstName() { return this.user.getFirstName(); }

    public String getLastName() { return this.user.getLastName(); }

    public int getId() { return this.user.getId(); }

    public List<Integer> getSubjects() {return this.user.getSubjects();}

    public LocalDate getDateOfBirth() { return this.user.getDateOfBirth(); }

    public User getUser() { return this.user; }
}
