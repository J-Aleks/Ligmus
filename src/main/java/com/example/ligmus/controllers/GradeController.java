package com.example.ligmus.controllers;

import com.example.ligmus.data.grades.Grade;
import com.example.ligmus.data.users.Student;
import com.example.ligmus.exception.ResourceNotFoundException;
import com.example.ligmus.services.LigmusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("student/{studentId}/grades")
public class GradeController {

    @Autowired
    LigmusService ligmusService;


    @GetMapping("/")
    public String ShowStudentGrade(@PathVariable int studentId, Model model) {
        List<Grade> grades = this.ligmusService.getGradesByUserId(studentId);
        if (grades.isEmpty()) {
            throw new ResourceNotFoundException("Student with id " + studentId + " don't have any grades");
        }
        model.addAttribute("studentId", studentId);
        model.addAttribute("grades",grades);
        return "grades";
    }


    @GetMapping("/add")
    public String showGradeForm(@PathVariable int studentId ,Model model){
        Student student = this.ligmusService.getStudent(studentId);
        if ( student == null) {
            throw new ResourceNotFoundException("Student with id " + studentId + " not found");
        }
        model.addAttribute("isUpdate", false);
        model.addAttribute("subjects", this.ligmusService.getSubjects());
        model.addAttribute("student",student);
        model.addAttribute("grade", new Grade());
        return "gradeForm";
    }

    @PostMapping("/add")
    public String saveGrade(@PathVariable int studentId, @ModelAttribute("grade") Grade newGrade){
        System.out.println("Saving grade");
        newGrade.setStudentId(studentId);
        newGrade.setGradeId(this.ligmusService.getNextGradeIndex());
        this.ligmusService.addGrade(newGrade);
        return "redirect:/student/" + studentId + "/grades/";
    }

    @GetMapping("/{gradeId}/update")
    public String updateGrade(@PathVariable int studentId, @PathVariable int gradeId, Model model){
        Student student = this.ligmusService.getStudent(studentId);
        if ( student == null) {
            throw new ResourceNotFoundException("Student with id " + studentId + " not found");
        }
        Grade grade = this.ligmusService.getGradeById(gradeId);
        if (grade == null) {
            throw new ResourceNotFoundException("Grade with id " + gradeId + " not found");
        }
        model.addAttribute("student",student);
        model.addAttribute("isUpdate", true);
        model.addAttribute("subjects", this.ligmusService.getSubjects());
        model.addAttribute("grade", grade);
        return "gradeForm";
    }

    @PostMapping("/{gradeId}/update")
    public String updateGrade(@PathVariable int studentId, @PathVariable int gradeId,
                              @ModelAttribute("grade") Grade grade){
        this.ligmusService.updateGradeById(gradeId, grade);
        return "redirect:/student/" + studentId + "/grades/";
    }
}
