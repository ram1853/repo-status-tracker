package com.event.processing.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/event")
public class EventController {

    @PostMapping("process")
    public void repoEvent(@RequestBody Object object, @RequestHeader("X-GitHub-Event") String githubEvent){
        System.out.println("githubEvent->"+githubEvent);
        System.out.println("object->"+object);
    }
}
