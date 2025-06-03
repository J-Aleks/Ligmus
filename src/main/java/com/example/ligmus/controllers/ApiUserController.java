package com.example.ligmus.controllers;



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
        int nextUserId = this.ligmusService.getNextUserId();
        String userType = newUser.getUserType();
        User user;
        switch(userType){
            case "student":
                user = new User(nextUserId, UserType.STUDENT);
                break;
            case "admin":
                user = new User(nextUserId, UserType.ADMIN);
                break;
            case "teacher":
                user = new User(nextUserId, UserType.TEACHER);
                break;
            default:
                return ResponseEntity.badRequest().body("Invalid user type");
        }
        user.setUsername(newUser.getUsername());
        user.setPassword(newUser.getPassword());
        this.ligmusService.addUser(user);
        return ResponseEntity.ok("User " + user.getUsername() + " added");
    }
    @PostMapping("/{id}/update")
    public ResponseEntity<String> updateUser(@PathVariable int id, @RequestBody  UserUpdateForm updateUser){
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