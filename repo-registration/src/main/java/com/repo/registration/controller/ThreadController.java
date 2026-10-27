package com.repo.registration.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
@RequestMapping("v1/ping")
public class ThreadController {

    private final Logger logger = Logger.getLogger(ThreadController.class.getName());

    @GetMapping
    public String currentThread(HttpServletRequest request) {
        logger.info("requestUri: " + request.getRequestURI());
        return Thread.currentThread().toString();
    }
}


