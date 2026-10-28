package com.repo.registration.benchmark;

import com.repo.registration.common.dto.request.RegistrationRequest;
import com.repo.registration.common.entity.RegistrationDetail;
import com.repo.registration.repository.IRegistrationRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.logging.Logger;

@Repository
@Primary
@Profile("benchmark")
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

    @Override
    public boolean saveRegistrationDetail(RegistrationDetail registrationDetail) {
        return false;
    }
}
