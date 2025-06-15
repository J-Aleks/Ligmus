package com.example.ligmus.controllers;

import com.example.ligmus.data.users.UserType;
import com.example.ligmus.security.auth.CustomUserDetails;
import com.example.ligmus.validation.groups.*;
import com.example.ligmus.data.users.User;
import com.example.ligmus.data.DTO.UserUpdateFormDTO;
import com.example.ligmus.exception.ResourceNotFoundException;
import com.example.ligmus.services.LigmusService;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/user")
public class UserController {

    final LigmusService ligmusService;

    public UserController(LigmusService ligmusService) {
        this.ligmusService = ligmusService;
    }

    @GetMapping("/")
    public String index() {
        return "redirect:/user/userDetails";
    }


    @GetMapping("/userDetails")
    public String showUserData( Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
        int id = userDetails.getId();
        User user = this.ligmusService.getUser(id);
        if ( user == null) {
            throw new ResourceNotFoundException("User with id " + id + " not found");
        }
        if(user.getUserType() == UserType.TEACHER ) {
            model.addAttribute("subjects", this.ligmusService.getTeacherSubjects(id));
        }
        String backUrl;
        backUrl = switch (userDetails.getUserType()) {
            case ADMIN -> "/admin-dev/";
            case TEACHER -> "/teacher/";
            case STUDENT ->  "/student/";
        };
        model.addAttribute("backUrl", backUrl);
        model.addAttribute("user",user);
        return "userDetails";
    }

    @GetMapping("/register")
    public String registerUser(@ModelAttribute("User") UserUpdateFormDTO updateUser, Model model) {
        model.addAttribute("User", updateUser);
        model.addAttribute("isRegister", true);
        return "updateUser";
    }
    @GetMapping("/update")
    public String updateUser(Model model,
                             @AuthenticationPrincipal CustomUserDetails userDetails) {

        UserUpdateFormDTO userDTO = new UserUpdateFormDTO();
        userDTO.setUsername(userDetails.getUsername());
        userDTO.setPassword(userDetails.getPassword());
        userDTO.setFirstName(userDetails.getFirstName());
        userDTO.setLastName(userDetails.getLastName());
        userDTO.setUserType(userDetails.getUserType().toString());
        userDTO.setDateOfBirth(userDetails.getDateOfBirth());

        model.addAttribute("isRegister", false);
        model.addAttribute("User", userDTO);
        return "updateUser";
    }
    @PostMapping("/register")
    public String registerUser(@ModelAttribute("User") @Validated(OnUpdate.class) UserUpdateFormDTO userUpdateFormDTO,
                             BindingResult result,
                             Model model,
                             @AuthenticationPrincipal CustomUserDetails userDetails) {
        System.out.println("przed walidacją");
        System.out.println(result);
        if(result.hasErrors()) {
            model.addAttribute("isRegister", true);
            return "updateUser";
        }
        int id = userDetails.getId();
        System.out.println("po walidacji");
        this.ligmusService.updateUserRegister(id, userUpdateFormDTO);
        String backUrl;
        backUrl = switch (userDetails.getUserType()) {
            case ADMIN -> "/admin-dev/";
            case TEACHER -> "/teacher/";
            case STUDENT -> "/student/";
        };
        return "redirect:"+backUrl;
    }

    @PostMapping("/update")
    public String updateUser(@ModelAttribute("User") @Validated(OnUpdate.class) UserUpdateFormDTO userUpdateFormDTO,
                             BindingResult result,
                             Model model,
                             @AuthenticationPrincipal CustomUserDetails userDetails) {
        System.out.println("przed walidacją");
        System.out.println(result);
        if(result.hasErrors()) {
            model.addAttribute("isRegister", false);
            return "updateUser";
        }
        int id = userDetails.getId();
        System.out.println("po walidacji");
        this.ligmusService.updateUser(id, userUpdateFormDTO);
        return "redirect:/user/";
    }


    @PostMapping("{id}/update")
    public String updateIdUser(@ModelAttribute("User") @Validated(OnUpdate.class) UserUpdateFormDTO userUpdateFormDTO,
                             BindingResult result,
                             @PathVariable int id, Model model) {
        System.out.println("przed walidacją");
        System.out.println(result);
        if(result.hasErrors()) {
            model.addAttribute("isRegister", true);
            return "updateUser";
        }
        System.out.println("po walidacji");
        this.ligmusService.updateUser(id, userUpdateFormDTO);
    return "redirect:/user/" + id;
    }
    @GetMapping("/{id}")
    public String showIdUserData(@PathVariable int id, Model model) {
        User user = this.ligmusService.getUser(id);
        if ( user == null) {
            throw new ResourceNotFoundException("User with id " + id + " not found");
        }
        model.addAttribute("user",user);
        return "userDetails";
    }

}
