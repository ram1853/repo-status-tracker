package com.repo.registration.repository;

import com.repo.registration.common.dto.request.RegistrationRequest;
import com.repo.registration.common.entity.RegistrationDetail;

public interface IRegistrationRepository {

    int registerWebHookForRepo(RegistrationRequest registrationRequest, String owner, String repo,
                                          String token);

    boolean saveRegistrationDetail(RegistrationDetail registrationDetail);
}
