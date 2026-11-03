package com.event.processing.controller;

import com.event.processing.repository.RepoMetricsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/event")
public class EventController {

    private final RepoMetricsRepository repoMetricsRepository;

    @Autowired
    public EventController(RepoMetricsRepository repoMetricsRepository) {
        this.repoMetricsRepository = repoMetricsRepository;
    }

    @PostMapping("process")
    public void repoEvent(@RequestBody Object object, @RequestHeader("X-GitHub-Event") String githubEvent){
        System.out.println("githubEvent->"+githubEvent);
        System.out.println("object->"+object);
        System.out.println("totalCount->"+repoMetricsRepository.count());
    }
}
