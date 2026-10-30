package com.event.processing.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/event")
public class EventController {

    @PostMapping("process")
    public void repoEvent(@RequestBody Object object){
        System.out.println("object->"+object);
    }
}
