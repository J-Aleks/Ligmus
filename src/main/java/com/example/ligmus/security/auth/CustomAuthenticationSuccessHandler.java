package com.example.ligmus.security.auth;

import com.example.ligmus.data.users.Student;
import com.example.ligmus.data.users.Teacher;
import com.example.ligmus.data.users.User;
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
        Object user = authentication.getPrincipal();
//        switch (role) {
//            case "ROLE_ADMIN":
//                redirectUrl = "/admin-dev";
//                break;
//            case "ROLE_TEACHER":
//                redirectUrl = "/";
        if (role.equals("ROLE_ADMIN")) {
            redirectUrl = redirectUrl + "admin-dev/";
        }
        if (user instanceof Student student) {
            if (student.getFirstName() == null ){
                redirectUrl = redirectUrl + "register";
            }
        }
        if (user instanceof Teacher teacher) {
            if (teacher.getFirstName() == null ){
                redirectUrl = redirectUrl + "register";
            }
        }
        response.sendRedirect(redirectUrl);
    }
}
