package com.repo.registration.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/ping")
public class HealthController {

    @GetMapping
    public String ping() {
        return "ok";
    }
}


