package com.repo.registration.repository;

import com.repo.registration.common.dto.request.RegistrationRequest;

public interface IRegistrationRepository {

    int registerWebHookForRepo(RegistrationRequest registrationRequest, String owner, String repo,
                                          String token);
}
