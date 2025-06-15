package com.example.ligmus.controllers;



import com.example.ligmus.data.DTO.UserUpdateFormDTO;
import com.example.ligmus.data.users.*;
import com.example.ligmus.services.LigmusService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class ApiUserController {

    final LigmusService ligmusService;

    public ApiUserController(LigmusService ligmusService) {
        this.ligmusService = ligmusService;
    }


    @GetMapping("/")
    public List<User> getAllUsers() {
        return ligmusService.getUsers();
    }

    @PostMapping("/add")
    public ResponseEntity<String> addUser(@RequestBody UserAddForm newUser){
        System.out.println("Odebrano dane JSON: " + newUser);
        this.ligmusService.addUser(newUser);
        return ResponseEntity.ok("User " + newUser.getUsername() + " added");
    }
    @PostMapping("/{id}/update")
    public ResponseEntity<String> updateUser(@PathVariable int id, @RequestBody UserUpdateFormDTO updateUser){
        System.out.println("Odebrano dane JSON: " + updateUser.toString());

        if(!this.ligmusService.updateUser(id, updateUser)){
            return ResponseEntity.badRequest().body("Invalid user Data");
        }

        return ResponseEntity.ok("User updated");
    }


    @PostMapping("/{id}/delete")
    public ResponseEntity<String> deleteUser(@PathVariable int id){
        System.out.println("Odebrano dane JSON: " + id);

        if(!this.ligmusService.deleteUser(id)){
            return ResponseEntity.badRequest().body("Invalid user id");
        }
        return ResponseEntity.ok("User deleted");
    }
}