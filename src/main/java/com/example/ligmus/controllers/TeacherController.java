package com.example.ligmus.controllers;


import com.example.ligmus.data.DTO.GradeFormDTO;
import com.example.ligmus.data.DTO.StudentsGradeDTO;
import com.example.ligmus.data.subjects.Subject;
import com.example.ligmus.data.users.User;
import com.example.ligmus.exception.ResourceNotFoundException;
import com.example.ligmus.services.LigmusService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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
        return "teacher";
    }


    @GetMapping("/GradesSerialForm")
    public String showGradeSerialForm(Model model, @RequestParam(value = "sort", required = false) String sort,
                                      @RequestParam(value = "subject", required = false) String subject,
                                      @CookieValue(value = "subjectCookie", required = false) String subjectCookie,
                                      @CookieValue(value = "sortCookie", required = false) String sortCookie,
                                      HttpServletResponse response, HttpSession session) {

//TODO: sortowanie usuwa wszytkie wprowadzone zmiany formularza
        String sortMethod = (sort != null) ? sort :
                (sortCookie != null) ? sortCookie : "id_asc";
        System.out.println("sortMethod: " + sortMethod);
        if (sort != null) {
            Cookie cookie = new Cookie("sortUserInFormCookie", sort);
            cookie.setPath("/");
            cookie.setMaxAge(24 * 60 * 60);
            response.addCookie(cookie);
        }


        int teacherId = (int) session.getAttribute("userId");
        List<Subject> teacherSubjectList = this.ligmusService.getTeacherSubjects(teacherId);
        if (teacherSubjectList.isEmpty()) {
            throw new IllegalStateException("Teacher not teach any subjects");
        }
        String selectedSubject = (subject != null) ? subject :
                (subjectCookie != null) ? subjectCookie :
                        teacherSubjectList.get(0).getName();
        System.out.println("selectedSubject: " + selectedSubject);
//        if (subject != null) {
//            Cookie cookie = new Cookie("subjectCookie", selectedSubject);
//            cookie.setPath("/");
//            cookie.setMaxAge(24 * 60 * 60);
//            response.addCookie(cookie);
//        }

        if (session.getAttribute("gradesSerialFormDraft" )== null) {

            List<User> students = this.ligmusService.sortUsers(this.ligmusService.getStudents(), sortMethod);
            if (students.isEmpty()) {
                throw new ResourceNotFoundException("No students found");
            }
            List<StudentsGradeDTO> studentsDTOList = new ArrayList<>();
            for (User student : students) {
                StudentsGradeDTO tempStudent = new StudentsGradeDTO();
                tempStudent.setId(student.getId());
                tempStudent.setFirstName(student.getFirstName());
                tempStudent.setLastName(student.getLastName());
                studentsDTOList.add(tempStudent);
            }
            GradeFormDTO gradeFormDTO = new GradeFormDTO();
            gradeFormDTO.setGrades(studentsDTOList);



//            model.addAttribute("selectedSubject", selectedSubject);
            model.addAttribute("form", gradeFormDTO);
        }
        else {
            boolean isUpdate = (boolean) session.getAttribute("isFormUpdate");
            if (isUpdate) {
                return "redirect:/teacherGradeSerialForm/update";
            }
//            model.addAttribute("selectedSubject", session.getAttribute("selectedSubject"));
            model.addAttribute("form", session.getAttribute("gradesSerialFormDraft"));
        }
        model.addAttribute("methodSelect" , sortMethod);
        model.addAttribute("subjects", teacherSubjectList);
        model.addAttribute("isUpdate", false);
        return "teacherGradeSerialForm";
    }

    @PostMapping("/GradesSerialForm/saveDraft")
    public String SaveGrades(@ModelAttribute("form") GradeFormDTO gradeFormDTO,
//                             @ModelAttribute("subjects") String subject,
                             HttpSession session) {
        session.setAttribute("gradesSerialFormDraft", gradeFormDTO);
//        session.setAttribute("selectedSubject", subject);
        session.setAttribute("isFormUpdate", false);

     return "index";
    }

}
