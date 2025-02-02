package com.propertyguru.controller;

import com.propertyguru.dtos.ApiResponse;
import com.propertyguru.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/user")

public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> signUpUser() {
        return ResponseEntity.ok(userService.addUser());
    }

}
