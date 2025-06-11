package com.example.ligmus.controllers;


import com.example.ligmus.data.grades.Grade;
import com.example.ligmus.data.subjects.Subject;
import com.example.ligmus.data.users.User;
import com.example.ligmus.exception.ResourceNotFoundException;
import com.example.ligmus.security.auth.CustomUserDetails;
import com.example.ligmus.services.LigmusService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    LigmusService ligmusService;

    @GetMapping("/")
    public String showMainStudents(){
        return "student-main";
    }
    @GetMapping("/grades")
    public String ShowStudentGrades(@AuthenticationPrincipal CustomUserDetails user, Model model) {

        int studentId = user.getId();
        List<Grade> grades = this.ligmusService.getGradesByUserId(studentId);
        model.addAttribute("isTeacher",false);
        model.addAttribute("studentId", studentId);
        model.addAttribute("grades",grades);
        return "grades";
    }

//    @GetMapping("/")
//    public String ShowStudents(Model model, @RequestParam(value = "sort", required = false) String sort,
//                               @CookieValue(value = "sortCookie", required = false) String sortCookie,
//                                HttpServletResponse response) {
//
//        String sortMethod = (sort != null) ? sort :
//                (sortCookie != null) ? sortCookie : "id_asc";
//        System.out.println("sortMethod: " + sortMethod);
//        if(sort != null) {
//            Cookie cookie = new Cookie("sortCookie", sort);
//            cookie.setPath("/");
//            cookie.setMaxAge( 60 * 60);
//            response.addCookie(cookie);
//        }
//        List <User> students = this.ligmusService.sortUsers(this.ligmusService.getStudents(), sortMethod);
//
//        System.out.println("Students List"+ students);
//        model.addAttribute("methodSelect" , sortMethod);
//        model.addAttribute("students",students);
//        return "students";
//    }

//    @GetMapping("/{studentId}/")



}
