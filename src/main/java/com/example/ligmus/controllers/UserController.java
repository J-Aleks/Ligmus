package com.example.ligmus.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class UserController {

    @GetMapping("/login")
    public String getItemsInCart(Model model) {
        model.addAttribute("cart", 0);
        return "login";
    }

    @GetMapping("/")
    public String Index() {
        return "index";
    }
}