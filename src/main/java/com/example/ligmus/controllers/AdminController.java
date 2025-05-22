package com.example.ligmus.controllers;


import com.example.ligmus.services.LigmusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/admin-dev")
public class AdminController {

    @Autowired
    LigmusService ligmusService;

    @GetMapping("/")
    public String adminMain(){
        return "admin-main";
    }

    @GetMapping("users")
    public String adminUsers(Model model)
    {
        System.out.println("Admin Users"+ this.ligmusService.getUsers());
        model.addAttribute("users", this.ligmusService.getUsers());
        return "admin-users";
    }

    @GetMapping("subjects")
    public String adminSubjects(){
        return "admin-subjects";
    }

    @GetMapping("grades")
    public String adminGrades(){
        return "admin-grades";
    }


//    @GetMapping("subjects")
//    public String adminStudentList (Model model) {
//        System.out.println("Students List"+this.ligmusService.getStudents());
//        model.addAttribute("students",this.ligmusService.getStudents());
//        return "students";
//    }

}
