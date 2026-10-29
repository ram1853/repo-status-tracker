package com.repo.registration.controller;

import com.repo.registration.common.dto.request.RegistrationRequest;
import com.repo.registration.common.dto.response.RegistrationResponse;
import com.repo.registration.service.IRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@RequestMapping("v1/registration")
public class RegistrationController {

    private static final Logger LOGGER = Logger.getLogger(RegistrationController.class.getName());

    private final IRegistrationService registrationService;

    @Autowired
    public RegistrationController(IRegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping
    public ResponseEntity<RegistrationResponse> registerWebHookForRepo(@RequestBody RegistrationRequest registrationRequest, @RequestHeader String owner,
                                                                       @RequestHeader String repo, @RequestHeader("Authorization") String token){
        LOGGER.info("registerWebHookForRepo, registrationRequest: "+ registrationRequest
        + ", owner: "+ owner+", repo: "+repo);

        int statusCode = registrationService.registerWebHookForRepo(registrationRequest, owner, repo, token);

        if(statusCode == HttpStatus.CREATED.value()) {
            if(registrationService.saveRegistrationDetail(registrationRequest, owner, repo)){
                return ResponseEntity.ok(new RegistrationResponse("Repo successfully registered"));
            }
            return ResponseEntity.status(HttpStatus.MULTI_STATUS)
                    .body(new RegistrationResponse("Repo registered, but state is not saved - contact Admin to reconcile state"));
        }
        return ResponseEntity.status(HttpStatusCode.valueOf(statusCode))
                .body(new RegistrationResponse("Repo registration failed"));
    }
}
