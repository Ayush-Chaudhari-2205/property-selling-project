package com.propertyselling.controller;


import com.propertyselling.dtos.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@RestController
public class HomeController {

    @GetMapping
    public ResponseEntity<?> renderHome() {

        HashMap<String, Object> hashmap = new HashMap<>();
        List<String> authors = Arrays.asList("Pradyumna Mahajan" , "Ayush Chaudhari", "Piyush Patil");
        hashmap.put("Authors", authors);
        hashmap.put("Project Name","Propery Selling");
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>("Project is Working! This message is from project creator", hashmap));
    }
}
