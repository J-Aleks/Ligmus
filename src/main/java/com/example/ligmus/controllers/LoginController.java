package com.example.ligmus.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class LoginController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }

//    @GetMapping("/register")
//    public String register() {
//        return "register";
//    }

//    @PostMapping("/register")
//    public String registryUser(@ModelAttribute("User") UserUpdateForm updateUser) {
//
//        int id = updateUser.get
//        ObjectMapper mapper = new ObjectMapper()
//                .registerModule(new JavaTimeModule())
//                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
//        String updateUserJson = mapper.writeValueAsString(updateUser);
//        System.out.println("JSON: "+ updateUserJson);
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        HttpEntity<String> entity = new HttpEntity<>(updateUserJson, headers);
//        ResponseEntity<String> response = restTemplate
//                .postForEntity("http://localhost:8080/Ligmus/api/users/{id}/update", entity, String.class, id);
//        System.out.println(response.getBody());
//        return "redirect:/admin-dev/users/"+id;
//    }
}
