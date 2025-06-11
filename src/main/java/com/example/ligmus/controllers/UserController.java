package com.example.ligmus.controllers;

import com.example.ligmus.validation.groups.*;
import com.example.ligmus.data.users.User;
import com.example.ligmus.data.users.UserUpdateForm;
import com.example.ligmus.exception.ResourceNotFoundException;
import com.example.ligmus.services.LigmusService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("user/{id}")
public class UserController {

    final LigmusService ligmusService;

    public UserController(LigmusService ligmusService) {
        this.ligmusService = ligmusService;
    }

    @GetMapping("/register")
    public String registerUser(@ModelAttribute("User") UserUpdateForm updateUser, Model model) {
        model.addAttribute("User", updateUser);
        model.addAttribute("isRegister", true);
        return "updateUser";
    }
    @GetMapping("/update")
    public String updateUser(Model model) {
        model.addAttribute("isRegister", false);
        model.addAttribute("User", new UserUpdateForm());
        return "updateUser";
    }
    @PostMapping("/update")
    public String updateUser(@ModelAttribute("User") @Validated(OnUpdate.class) UserUpdateForm userUpdateForm,
                             BindingResult result,
                             @PathVariable int id, Model model) {
        System.out.println("przed walidacją");
        System.out.println(result);
        if(result.hasErrors()) {
            model.addAttribute("isRegister", true);
            return "updateUser";
        }
        System.out.println("po walidacji");
        this.ligmusService.updateUser(id,userUpdateForm);
    return "index";
    }
    @GetMapping("/")
    public String showUserData(@PathVariable int id, Model model) {
        User user = this.ligmusService.getUser(id);
        if ( user == null) {
            throw new ResourceNotFoundException("User with id " + id + " not found");
        }
        model.addAttribute("user",user);
        return "userDetails";
    }
}
