package com.example.ligmus.security.auth;



import com.example.ligmus.data.users.UserType;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        String redirectUrlDefault = "/Ligmus/";
        String redirectUrl = redirectUrlDefault;
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        UserType userType = userDetails.getUserType();
        redirectUrl = switch (userType) {
            case ADMIN -> redirectUrl + "admin-dev/";
            case TEACHER -> redirectUrl + "teacher/";
            case STUDENT -> redirectUrl + "student/";
        };
        if(userDetails.getFirstName() == null) {
            redirectUrl = redirectUrlDefault + "user/register";
        }
        response.sendRedirect(redirectUrl);
    }
}
