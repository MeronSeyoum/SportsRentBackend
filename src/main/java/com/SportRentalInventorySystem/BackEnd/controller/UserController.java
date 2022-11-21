package com.SportRentalInventorySystem.BackEnd.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SportRentalInventorySystem.BackEnd.ExceptionHandler.ResourceNotFoundException;
import com.SportRentalInventorySystem.BackEnd.model.User;
import com.SportRentalInventorySystem.BackEnd.repository.UserRepository;

@CrossOrigin(origins = "*" ) 
@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;
     
   
    @GetMapping("/user")
    @PreAuthorize("hasRole('ADMIN')")
    public  List<User> getUser(){
        return userRepository.findAll();
    }
    
    
//    Retrieve User profile data
    @GetMapping("/getUserProfileById/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_USER') OR hasRole('ROLE_MODERATOR')")
    public ResponseEntity<User> getProfile(@PathVariable long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not exist with id:" + id));
        return ResponseEntity.ok(user);
    }
}