package com.example.ligmus.controllers;


import com.example.ligmus.data.grades.Grade;
import com.example.ligmus.security.auth.CustomUserDetails;
import com.example.ligmus.services.LigmusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
        String studentFullName = this.ligmusService.getUserFullName(studentId);
        Map<Integer, String> SubjectDescriptions = this.ligmusService.getSubjectNamesForId();
        List<Grade> grades = this.ligmusService.getGradesByUserId(studentId);
        model.addAttribute("isTeacher",false);
        model.addAttribute("studentFullName", studentFullName);
        model.addAttribute("grades",grades);
        model.addAttribute("subjectMap",SubjectDescriptions);
        return "grades";
    }
}
