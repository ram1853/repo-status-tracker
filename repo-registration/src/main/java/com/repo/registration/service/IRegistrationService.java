package com.repo.registration.service;

import com.repo.registration.common.dto.request.RegistrationRequest;

public interface IRegistrationService {

    int registerWebHookForRepo(RegistrationRequest registrationRequest, String owner, String repo,
                                String token);

    boolean saveRegistrationDetail(RegistrationRequest registrationRequest, String owner, String repo);
}
