package com.example.ligmus.controllers;


import com.example.ligmus.data.DTO.GradeDTO;
import com.example.ligmus.data.DTO.GradeFormDTO;
import com.example.ligmus.data.DTO.StudentsAddGradeDTO;
import com.example.ligmus.data.Entities.SubjectEntity;
import com.example.ligmus.data.grades.Grade;
import com.example.ligmus.data.subjects.Subject;
import com.example.ligmus.data.users.User;
import com.example.ligmus.exception.ResourceNotFoundException;
import com.example.ligmus.security.auth.CustomUserDetails;
import com.example.ligmus.services.LigmusService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/teacher")
public class TeacherController {

    final
    LigmusService ligmusService;

    public TeacherController(LigmusService ligmusService) {
        this.ligmusService = ligmusService;
    }

    @GetMapping("/")
    public String showTeacherIndex(){
        return "teacher-main";
    }


    @GetMapping("/GradesSerialForm")
    public String showGradeSerialForm(Model model, @RequestParam(value = "sort", required = false) String sort,
                                      @RequestParam(value = "subject", required = false) String subject,
                                      @CookieValue(value = "subjectCookie", required = false) String subjectCookie,
                                      HttpServletResponse response, HttpSession session,
                                      @AuthenticationPrincipal CustomUserDetails user) {


        int teacherId = user.getId();
        List<SubjectEntity> teacherSubjectList = this.ligmusService.getTeacherSubjects(teacherId);
        if (teacherSubjectList.isEmpty()) {
            throw new IllegalStateException("Teacher not teach any subjects");
        }
        String selectedSubject = (subject != null) ? subject :
                (subjectCookie != null) ? subjectCookie :
                        teacherSubjectList.get(0).getName();
        System.out.println("selectedSubject: " + selectedSubject);
        if (subject != null) {
            Cookie cookie = new Cookie("subjectCookie", selectedSubject);
            cookie.setPath("/");
            cookie.setMaxAge(24 * 60 * 60);
            response.addCookie(cookie);
        }
        GradeFormDTO gradeFormDTO = new GradeFormDTO();
        if (session.getAttribute("gradesSerialFormDraft" )== null) {
            if(gradeFormDTO.getGrades() == null) {
                List<User> students = this.ligmusService.getStudents();
                if (students.isEmpty()) {
                    throw new ResourceNotFoundException("No students found");
                }
                List<StudentsAddGradeDTO> studentsDTOList = new ArrayList<>();
                for (User student : students) {
                    StudentsAddGradeDTO tempStudent = new StudentsAddGradeDTO();
                    tempStudent.setId(student.getId());
                    tempStudent.setFirstName(student.getFirstName());
                    tempStudent.setLastName(student.getLastName());
                    studentsDTOList.add(tempStudent);
                    gradeFormDTO.setGrades(studentsDTOList);
                }
            }
//            else {
//                gradeFormDTO.setGrades(gradeFormDTO.sortStudents(gradeFormDTO.getGrades(), sortMethod));
//            }
//            model.addAttribute("selectedSubject", selectedSubject);
            model.addAttribute("form", gradeFormDTO);
        }
        else {
            GradeFormDTO tempForm = (GradeFormDTO) session.getAttribute("gradesSerialFormDraft");
            boolean isUpdate = (boolean) session.getAttribute("isFormUpdate");
            if (isUpdate) {
                return "redirect:/teacherGradeSerialForm/update";
            }
//            model.addAttribute("selectedSubject", session.getAttribute("selectedSubject"));
//            model.addAttribute("form", session.getAttribute("gradesSerialFormDraft"));
            model.addAttribute("form", tempForm);
        }
        model.addAttribute("subjects", teacherSubjectList);
        model.addAttribute("isUpdate", false);
        return "teacherGradeSerialForm";
    }

    @PostMapping("/GradesSerialForm/saveDraft")
    public String saveDraftGrades(@ModelAttribute("form") GradeFormDTO gradeFormDTO,
                             HttpSession session) {
        session.setAttribute("gradesSerialFormDraft", gradeFormDTO);
        session.setAttribute("isFormUpdate", false);

     return "teacher-main";
    }

    @PostMapping("/GradesSerialForm/add")
    public String saveGrades(@ModelAttribute("form") GradeFormDTO gradeForm,
                             HttpSession session) {
        int subId = gradeForm.getSubject();
        if(subId == -1) {
            throw new ResourceNotFoundException("Subject not found");
        }
        for (StudentsAddGradeDTO studentsAddGradeDTO : gradeForm.getGrades()) {
            if(studentsAddGradeDTO.getGrade() == 0 || studentsAddGradeDTO.getWeight() == 0){
                continue;
            }
            Grade tempGrade = new Grade();
            tempGrade.setGradeId(this.ligmusService.getNextGradeIndex());
            tempGrade.setStudentId(studentsAddGradeDTO.getId());
            tempGrade.setTeacherId((int) session.getAttribute("userId"));
            tempGrade.setSubject(subId);
            tempGrade.setWeight(studentsAddGradeDTO.getWeight());
            tempGrade.setGrade(studentsAddGradeDTO.getGrade());
            tempGrade.setDescription(studentsAddGradeDTO.getDescription());
            this.ligmusService.addGrade(tempGrade);
        }
        System.out.println(this.ligmusService.getGradesByUserId(subId));
        return "index";
    }


    @GetMapping("/students/")
    public String ShowStudents(Model model, @RequestParam(value = "sort", required = false) String sort,
                               @CookieValue(value = "sortCookie", required = false) String sortCookie,
                               HttpServletResponse response) {

        String sortMethod = (sort != null) ? sort :
                (sortCookie != null) ? sortCookie : "id_asc";
        System.out.println("sortMethod: " + sortMethod);
        if(sort != null) {
            Cookie cookie = new Cookie("sortCookie", sort);
            cookie.setPath("/");
            cookie.setMaxAge( 60 * 60);
            response.addCookie(cookie);
        }
        List <User> students = this.ligmusService.sortUsers(this.ligmusService.getStudents(), sortMethod);

        System.out.println("Students List"+ students);
        model.addAttribute("methodSelect" , sortMethod);
        model.addAttribute("students",students);
        return "students";
    }
    @GetMapping("/students/{studentId}/")
    public String redirectToGrades(@PathVariable int studentId) {
        return "redirect:/teacher/students/" + studentId + "/grades/";
    }


}
