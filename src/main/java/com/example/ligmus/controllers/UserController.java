package com.example.ligmus.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin-dev/users1/")
public class UserController {

    @GetMapping("/login")
    public String getItemsInCart(Model model) {
        return "login";
    }

    @GetMapping("/")
    public String Index() {
        return "index";
    }
}