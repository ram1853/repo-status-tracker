package com.repo.registration.repository.impl;

import com.repo.registration.common.dto.request.RegistrationRequest;
import com.repo.registration.repository.IRegistrationRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import java.util.logging.Logger;

@Repository
@ConditionalOnProperty(name = "benchmark.test", havingValue = "true")
public class RegistrationRepositoryStub implements IRegistrationRepository {

    private final Logger LOGGER = Logger.getLogger(RegistrationRepositoryStub.class.getName());

    private static final int SIMULATED_IO_DELAY_MS = 300;

    @Override
    public int registerWebHookForRepo(RegistrationRequest registrationRequest, String owner,
                                      String repo, String token) {
        LOGGER.info("RegistrationRepositoryStub:registerWebHookForRepo, simulating "
                + SIMULATED_IO_DELAY_MS + "ms I/O wait");
        try {
            Thread.sleep(SIMULATED_IO_DELAY_MS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return 201;
    }
}
