package com.example.ligmus.controllers;


import com.example.ligmus.data.grades.Grade;
import com.example.ligmus.data.users.Student;
import com.example.ligmus.exception.ResourceNotFoundException;
import com.example.ligmus.services.LigmusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    LigmusService ligmusService;

    @GetMapping("/")
    public String ShowStudents(Model model) {
        System.out.println("Students List"+this.ligmusService.getStudents());
        model.addAttribute("students",this.ligmusService.getStudents());
        return "students";
    }

    @GetMapping("/{studentId}")
    public String ShowStudentById(@PathVariable int studentId, Model model) {
        Student student = this.ligmusService.getStudent(studentId);
        if ( student == null) {
            throw new ResourceNotFoundException("Student with id " + studentId + " not found");
        }
        model.addAttribute("student",this.ligmusService.getStudent(studentId));
        return "student";
    }

}
