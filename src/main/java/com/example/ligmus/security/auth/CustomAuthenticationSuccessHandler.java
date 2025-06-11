package com.example.ligmus.security.auth;



import com.example.ligmus.data.users.UserType;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        String redirectUrlDefault = "/Ligmus/";
        String redirectUrl = redirectUrlDefault;
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        UserType userType = userDetails.getUserType();
        switch (userType) {
            case ADMIN:
                redirectUrl = redirectUrl + "admin-dev/";
                break;
            case TEACHER:
                redirectUrl = redirectUrl + "teacher/";
                break;
            case STUDENT:
                redirectUrl = redirectUrl + "student/";
                break;
        }
        if(userDetails.getFirstName() == null) {
            int userId = userDetails.getId();
            redirectUrl = redirectUrl + "user/"+userId+"/register";
        }
        HttpSession session = request.getSession();
        session.setAttribute("userId", userDetails.getId());

        response.sendRedirect(redirectUrl);
    }
}
