package com.example.ligmus.controllers;


import com.example.ligmus.data.DTO.GradeDTO;
import com.example.ligmus.data.DTO.SubjectDTO;
import com.example.ligmus.data.DTO.UserUpdateFormDTO;
import com.example.ligmus.data.subjects.Subject;
import com.example.ligmus.data.users.*;
import com.example.ligmus.exception.ResourceNotFoundException;
import com.example.ligmus.security.auth.CustomUserDetails;
import com.example.ligmus.services.LigmusService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.web.client.RestTemplate;

import java.util.List;

@Controller
@RequestMapping("/admin-dev")
public class AdminController {

    @Autowired
    LigmusService ligmusService;

    final RestTemplate restTemplate;

    public AdminController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/")
    public String adminMain(){
        return "admin-main";
    }

    @GetMapping("/users")
    public String showUsers(Model model){

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<List<User>> response = restTemplate.exchange("http://localhost:8088/Ligmus/api/users/",
                HttpMethod.GET, entity, new ParameterizedTypeReference<>(){});

        System.out.println("JSON body: "+ response.getBody());
        List<User> users = response.getBody();
        model.addAttribute("users", users);
        return "admin-users";
    }


    @GetMapping("/users/addUser")
    public String addUser(Model model){

        model.addAttribute("subjects", "");
        model.addAttribute("allSubjects", this.ligmusService.getSubjects());
        model.addAttribute("isUpdate", false);
        model.addAttribute("newUser", new UserAddForm());

        return "userForm";
    }

    @PostMapping("/users/addUser")
    public String saveUser(@ModelAttribute("newUser") UserAddForm newUser) throws JsonProcessingException {
        System.out.println("AdminContr add new user");
        ObjectMapper mapper = new ObjectMapper();
        String newUserJson = mapper.writeValueAsString(newUser);
        System.out.println("JSON: "+ newUserJson);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(newUserJson, headers);
        ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8088/Ligmus/api/users/add"
                , entity, String.class);


        System.out.println(response.getBody());

        return "redirect:/admin-dev/users";
    }

    @GetMapping("/users/{id}")
    public String showUser(@PathVariable int id, Model model){
        User user = ligmusService.getUser(id);
        if(user == null){
            throw new RuntimeException("User not found");
        }
        if(user.getUserType() == UserType.TEACHER){
            System.out.println("AdminContr show Teacher");
            model.addAttribute("isTeacher", true);
            model.addAttribute("subjects", this.ligmusService.getTeacherSubjects(user.getId()));
        }
        else{
            model.addAttribute("isTeacher", false);
        }
        model.addAttribute("user", user);
        model.addAttribute("id", id);
        return "admin-user";
    }


    @GetMapping("/users/{id}/update")
    public String updateUser(@PathVariable int id, Model model){

        User user = this.ligmusService.getUser(id);
        if(user.getUserType() == UserType.TEACHER){
            model.addAttribute("isTeacher", true);
            model.addAttribute("subjects", this.ligmusService.getTeacherSubjects(user.getId()));
            model.addAttribute("allSubjects", this.ligmusService.getSubjects());
        }
        model.addAttribute("newUser", user);
        model.addAttribute("isUpdate", true);
        return "userForm";
    }

    @PostMapping("/users/{id}/update")
    public String updateUser(@PathVariable int id,
                             @ModelAttribute("newUser") UserUpdateFormDTO updateUser) throws JsonProcessingException {
        System.out.println("AdminContr update user");
        ObjectMapper mapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        String updateUserJson = mapper.writeValueAsString(updateUser);
        System.out.println("JSON: "+ updateUserJson);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(updateUserJson, headers);
        ResponseEntity<String> response = restTemplate
                .postForEntity("http://localhost:8088/Ligmus/api/users/{id}/update", entity, String.class, id);
        System.out.println(response.getBody());
        return "redirect:/admin-dev/users/"+id;
    }

    @GetMapping("/subjects")
    public String adminSubjects(Model model){

        List<Subject> subjects = ligmusService.getSubjects();
        model.addAttribute("subjects", subjects);
        return "admin-subjects";
    }

    @GetMapping("/subjects/{id}")
    public String showSubject(@PathVariable int id, Model model){
        SubjectDTO subject = this.ligmusService.getSubjectDTO(id);
        if(subject == null){
            throw new ResourceNotFoundException("Subject not found");
        }
        model.addAttribute("subject", subject);
        return "admin-subject";
    }

    @GetMapping("/subjects/{id}/delete")
    public String deleteSubject(@PathVariable int id){
        this.ligmusService.deleteSubject(id);
        return "redirect:/admin-dev/subjects";
    }

    @GetMapping("/subjects/addSubject")
    public String addSubject(Model model){
        model.addAttribute("isUpdate", false);
        model.addAttribute("subject", new SubjectDTO());
        return "subjectForm";
    }

    @PostMapping("/subjects/addSubject")
    public String saveSubject(@ModelAttribute("subject") SubjectDTO subjectDTO) {
        this.ligmusService.addSubject(subjectDTO);
        return "redirect:/admin-dev/subjects";
    }

    @GetMapping("/subjects/{id}/update")
    public String updateSubject(@PathVariable int id, Model model){

        SubjectDTO subject = this.ligmusService.getSubjectDTO(id);
        model.addAttribute("subject", subject);
        model.addAttribute("isUpdate", true);
        return "subjectForm";
    }

    @PostMapping("/subjects/{id}/update")
    public String updateSubject(@PathVariable int id, @ModelAttribute("subject") SubjectDTO subjectDTO){
        this.ligmusService.updateSubject(id, subjectDTO);
        return "redirect:/admin-dev/subjects";
    }

    @GetMapping("grades")
    public String adminGrades(Model model){
        model.addAttribute("grades", this.ligmusService.getGradesId());
        return "admin-grades";
    }

    @GetMapping("/grades/{id}")
    public String showGrade(@PathVariable int id, Model model){
        GradeDTO grade = this.ligmusService.getGradeDTOById(id);
        if(grade == null){
            throw new ResourceNotFoundException("Subject not found");
        }
        model.addAttribute("teacherFullName", this.ligmusService.getUserFullName(grade.getTeacherId()));
        model.addAttribute("studentFullName", this.ligmusService.getUserFullName(grade.getStudentId()));
        model.addAttribute("grade", grade);
        return "admin-grade";
    }


    @GetMapping("/users/{id}/delete")
    public String deleteUser(@PathVariable int id, @AuthenticationPrincipal CustomUserDetails userDetails){
        if(id == userDetails.getId()){
            System.out.println("You can't delete your own user");
            return "redirect:/admin-dev/users";
        }
        System.out.println("AdminContr delete user");
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate
                .postForEntity("http://localhost:8088/Ligmus/api/users/{id}/delete", entity, String.class, id);
        System.out.println(response.getBody());
        return "redirect:/admin-dev/users";
    }

}
