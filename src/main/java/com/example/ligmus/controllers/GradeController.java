package com.example.ligmus.controllers;

import com.example.ligmus.data.DTO.GradeDTO;
import com.example.ligmus.data.Entities.SubjectEntity;
import com.example.ligmus.data.grades.Grade;
import com.example.ligmus.data.subjects.Subject;
import com.example.ligmus.data.users.*;
import com.example.ligmus.exception.ResourceNotFoundException;
import com.example.ligmus.security.auth.CustomUserDetails;
import com.example.ligmus.services.LigmusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("teacher/students/{studentId}/grades")
public class GradeController {

    @Autowired
    LigmusService ligmusService;

//    @GetMapping("/")
//    public String ShowStudentGrades(@PathVariable int studentId, Model model,
//                                    @AuthenticationPrincipal CustomUserDetails user,
//                                    HttpServletRequest request) {
//        List<Grade> grades = new ArrayList<>();
//        if (request.isUserInRole("ROLE_TEACHER")) {
//            int teacherId = user.getId();
//            List<Subject> TeacherSubjectList = this.ligmusService.getTeacherSubjects(teacherId);
//            for (Subject subject : TeacherSubjectList) {
//                grades.addAll(this.ligmusService.getStudentGradesFromSubject(studentId, subject.getId()));
//                System.out.println(grades);
//            }
//            model.addAttribute("isTeacher",true);
//        }
//        else if (request.isUserInRole("ROLE_ADMIN"))
//        {
//            grades = this.ligmusService.getGradesByUserId(studentId);
//            model.addAttribute("isTeacher",true);
//        }
//        else if (request.isUserInRole("ROLE_STUDENT"))
//        {
//            grades = this.ligmusService.getGradesByUserId(studentId);
//            model.addAttribute("isTeacher",false);
//        }
//        model.addAttribute("studentId", studentId);
//        model.addAttribute("grades",grades);
//        return "grades";
//    }

    @GetMapping("/")
    public String ShowStudentGrades(@PathVariable int studentId, Model model,
                                    @AuthenticationPrincipal CustomUserDetails user) {
        List<GradeDTO> gradeDTO = new ArrayList<>();
        int teacherId = user.getId();
        Map<Integer, String> SubjectDescriptions = this.ligmusService.getSubjectNamesForId();
        List<SubjectEntity> TeacherSubjectList = this.ligmusService.getTeacherSubjects(teacherId);
        for (SubjectEntity subject : TeacherSubjectList) {
            gradeDTO.addAll(this.ligmusService.getStudentGradesFromSubjectDTO(studentId, subject.getId()));
            System.out.println(gradeDTO);
        }

        String studentFullName = this.ligmusService.getUserFullName(studentId);
        model.addAttribute("isTeacher",true);
        model.addAttribute("studentFullName", studentFullName);
        model.addAttribute("grades",gradeDTO);
        model.addAttribute("subjectMap",SubjectDescriptions);
        return "grades";
    }

    @GetMapping("/add")
    public String showGradeForm(@PathVariable int studentId ,Model model, @AuthenticationPrincipal CustomUserDetails user){
        User student = this.ligmusService.getStudent(studentId);
        if ( student == null) {
            throw new ResourceNotFoundException("Student with id " + studentId + " not found");
        }
        model.addAttribute("isUpdate", false);
        model.addAttribute("subjects", this.ligmusService.getTeacherSubjects(user.getId()));
        model.addAttribute("student",student);
        model.addAttribute("grade", new GradeDTO());
        return "gradeForm";
    }

    @PostMapping("/add")
    public String saveGrade(@PathVariable int studentId, @ModelAttribute("grade")  @Validated GradeDTO newGrade,
                            BindingResult bindingResult, Model model,
                            @AuthenticationPrincipal CustomUserDetails user) {

        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getAllErrors());
            model.addAttribute("isUpdate", false);
            model.addAttribute("subjects", this.ligmusService.getTeacherSubjects(user.getId()));
            model.addAttribute("student",this.ligmusService.getStudent(studentId));
            model.addAttribute("grade",newGrade);
            return "gradeForm";
        }

        System.out.println("Saving grade");
        newGrade.setTeacherId(user.getId());
        newGrade.setStudentId(studentId);
        newGrade.setGradeId(this.ligmusService.getNextGradeIndex());
        this.ligmusService.addGrade(newGrade);
        return "redirect:/teacher/students/" + studentId + "/grades/";
    }

    @GetMapping("/{gradeId}/update")
    public String updateGrade(@PathVariable int studentId, @PathVariable int gradeId, Model model){
        User student = this.ligmusService.getStudent(studentId);
        if ( student == null) {
            throw new ResourceNotFoundException("Student with id " + studentId + " not found");
        }
        GradeDTO grade = this.ligmusService.getGradeDTOById(gradeId);
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
                              @ModelAttribute("grade") @Validated GradeDTO grade,
                              BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("isUpdate", true);
            model.addAttribute("subjects", this.ligmusService.getSubjects());
            model.addAttribute("student", this.ligmusService.getStudent(studentId));
            model.addAttribute("grade",grade);
            return "gradeForm";
        }


        this.ligmusService.updateGradeById(gradeId, grade);
        return "redirect:/teacher/students/" + studentId + "/grades/";
    }
}
