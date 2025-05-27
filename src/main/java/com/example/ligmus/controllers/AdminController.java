package com.example.ligmus.controllers;


import com.example.ligmus.data.users.*;
import com.example.ligmus.services.LigmusService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

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
    public String showUsers(Model model)
    {
        System.out.println("Admin Users"+ this.ligmusService.getUsers());
        model.addAttribute("users", this.ligmusService.getUsers());
        return "admin-users";
    }

    @GetMapping("/users/addUser")
    public String addUser(Model model){

        model.addAttribute("isUpdate", false);
        model.addAttribute("newUser", new UserForm());

        return "userForm";
    }

    @PostMapping("/users/addUser")
    public String saveUser(@ModelAttribute("newUser") UserForm newUser) throws JsonProcessingException {
        System.out.println("AdminContr add new user");
        ObjectMapper mapper = new ObjectMapper();
        String newUserJson = mapper.writeValueAsString(newUser);
        System.out.println("JSON: "+ newUserJson);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(newUserJson, headers);
        ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8080/Ligmus/api/users/add"
                , entity, String.class);

        System.out.println(response.getBody());

        return "redirect:/admin-dev/users";
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
