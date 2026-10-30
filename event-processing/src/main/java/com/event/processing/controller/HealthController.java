package com.event.processing.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/event")
public class HealthController {

    @GetMapping("ping")
    public String ping() {
        return "ok";
    }
}
