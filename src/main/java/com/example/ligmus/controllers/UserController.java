package com.example.ligmus.controllers;



import com.example.ligmus.data.users.*;
import com.example.ligmus.services.LigmusService;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    final LigmusService ligmusService;

    public UserController(LigmusService ligmusService) {
        this.ligmusService = ligmusService;
    }


//    @GetMapping("/add")
//    public String addUser(Model model){
//
//        System.out.println("Adding user");
//        model.addAttribute("isUpdate", false);
//        model.addAttribute("newUser", new UserForm());
//
//        return "userForm";
//    }

    @PostMapping("/add")
    public ResponseEntity<String> addUser(@RequestBody  UserForm newUser){
        System.out.println("Odebrano dane JSON: " + newUser);
        int nextUserId = this.ligmusService.getNextUserId();
        String userType = newUser.getUserType();
        User user;
        switch(userType){
            case "student":
                user = new Student(nextUserId);
                break;
            case "admin":
                user = new Admin(nextUserId);
                break;
            case "teacher":
                user = new Teacher(nextUserId);
                break;
            default:

                return ResponseEntity.badRequest().body("Invalid user type");
        }
        user.setUsername(newUser.getUsername());
        user.setPassword(newUser.getPassword());
        this.ligmusService.addUser(user);
        return ResponseEntity.ok("User " + user.getUsername() + " added");
    }
}