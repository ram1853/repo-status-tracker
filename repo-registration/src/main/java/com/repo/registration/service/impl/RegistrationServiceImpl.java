package com.repo.registration.service.impl;

import com.repo.registration.common.dto.request.RegistrationRequest;
import com.repo.registration.common.entity.RegistrationDetail;
import com.repo.registration.repository.IRegistrationRepository;
import com.repo.registration.service.IRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.logging.Logger;

@Service
public class RegistrationServiceImpl implements IRegistrationService {

    private final Logger LOGGER = Logger.getLogger(RegistrationServiceImpl.class.getName());

    private final IRegistrationRepository registrationRepository;

    @Autowired
    public RegistrationServiceImpl(IRegistrationRepository registrationRepository) {
        this.registrationRepository = registrationRepository;
    }

    @Override
    public int registerWebHookForRepo(RegistrationRequest registrationRequest, String owner, String repo,
                                       String token) {
        return registrationRepository.registerWebHookForRepo(registrationRequest, owner, repo, token);
    }

    @Override
    public boolean saveRegistrationDetail(RegistrationRequest registrationRequest, String owner, String repo) {
        RegistrationDetail registrationDetail = new RegistrationDetail();
        registrationDetail.setOwner(owner);
        registrationDetail.setRepo(repo);
        registrationDetail.setEventUrl(registrationRequest.config().url());
        registrationDetail.setEvents(registrationRequest.events());
        registrationDetail.setCreatedAt(Instant.now().toString());
        return registrationRepository.saveRegistrationDetail(registrationDetail);
    }
}
