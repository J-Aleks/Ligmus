package com.example.ligmus.security.auth;

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
                                        Authentication authentication) throws IOException, ServletException {
        String redirectUrl = "/Ligmus/";
        String role = authentication.getAuthorities().iterator().next().getAuthority();
//        switch (role) {
//            case "ROLE_ADMIN":
//                redirectUrl = "/admin-dev";
//                break;
//            case "ROLE_TEACHER":
//                redirectUrl = "/";
        if (role.equals("ROLE_ADMIN")) {
            redirectUrl = redirectUrl + "admin-dev/";
        }
        response.sendRedirect(redirectUrl);
    }
}
