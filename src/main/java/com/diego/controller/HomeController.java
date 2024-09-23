package com.diego.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class HomeController {

    @GetMapping
    public String homeController() {
        return "Bienvenido a la API de Diego";
    }

    // @PostMapping
    // @PutMapping
    // @DeleteMapping
    
    

}
