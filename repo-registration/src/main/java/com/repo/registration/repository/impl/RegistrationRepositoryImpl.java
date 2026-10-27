package com.repo.registration.repository.impl;

import com.repo.registration.common.dto.request.RegistrationRequest;
import com.repo.registration.repository.IRegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.logging.Logger;

@Repository
public class RegistrationRepositoryImpl implements IRegistrationRepository {

    private final Logger LOGGER = Logger.getLogger(RegistrationRepositoryImpl.class.getName());

    @Value("${github.host}")
    private String githubHost;

    @Value("${github.scheme}")
    private String githubScheme;

    @Value("${github.port}")
    private String githubPort;

    private final RestClient restClient;

    @Autowired
    public RegistrationRepositoryImpl(RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public int registerWebHookForRepo(RegistrationRequest registrationRequest, String owner, String repo,
                                                 String token) {
        LOGGER.info("RegistrationRepositoryImpl:registerWebHookForRepo, calling github webhook API");

        URI uri = UriComponentsBuilder.newInstance().scheme(githubScheme)
                .host(githubHost)
                .port(githubPort)
                .path("repos/")
                .path(owner)
                .path("/")
                .path(repo)
                .path("/")
                .path("hooks")
                .build().toUri();

        ResponseEntity<Void> response = restClient.post()
                .uri(uri)
                .header("Authorization", "Bearer "+token)
                .body(registrationRequest)
                .retrieve()
                .toBodilessEntity();

        LOGGER.info("RegistrationRepositoryImpl:registerWebHookForRepo, response "+ response.getStatusCode());
        return response.getStatusCode().value();
    }
}
