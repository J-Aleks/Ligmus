package com.example.ligmus.controllers;


import com.example.ligmus.data.users.User;
import com.example.ligmus.data.users.UserUpdateForm;
import com.example.ligmus.exception.ResourceNotFoundException;
import com.example.ligmus.services.LigmusService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("user")
public class UserController {

    final LigmusService ligmusService;

    public UserController(LigmusService ligmusService) {
        this.ligmusService = ligmusService;
    }

    @GetMapping("/{id}/register")
    public String registerUser(@PathVariable int id ,@ModelAttribute("User") UserUpdateForm updateUser, Model model) {
        model.addAttribute("User", updateUser);
        model.addAttribute("isRegister", true);
        return "updateUser";
    }
    @GetMapping("/{id}/update")
    public String updateUser(Model model) {
        model.addAttribute("isRegister", false);
        model.addAttribute("User", new UserUpdateForm());
        return "updateUser";
    }
    @PostMapping("/{id}/update")
    public String updateUser(@ModelAttribute("User") UserUpdateForm userUpdateForm, @PathVariable int id) {
        this.ligmusService.updateUser(id,userUpdateForm);
    return "index";
    }
    @GetMapping("/{id}")
    public String showUserData(@PathVariable int id, Model model) {
        User user = this.ligmusService.getUser(id);
        if ( user == null) {
            throw new ResourceNotFoundException("User with id " + id + " not found");
        }
        model.addAttribute("user",user);
        return "userDetails";
    }
}
