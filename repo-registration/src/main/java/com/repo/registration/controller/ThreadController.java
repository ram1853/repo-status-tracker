package com.repo.registration.controller;

<<<<<<< HEAD
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
    public String currentThread(HttpServletRequest request){
        logger.info("requestUri: "+request.getRequestURI());
=======
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ThreadController {

    @GetMapping("thread")
    public String currentThread(){
>>>>>>> a19b37bc30b633154055f31fc4209a12b45f4a92
        return Thread.currentThread().toString();
    }
}
