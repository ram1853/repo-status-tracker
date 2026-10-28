package com.repo.registration.repository.impl;

import com.repo.registration.common.constant.Constants;
import com.repo.registration.common.dto.request.RegistrationRequest;
import com.repo.registration.common.entity.RegistrationDetail;
import com.repo.registration.repository.IRegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;

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

    private final DynamoDbEnhancedClient dynamoDbEnhancedClient;

    @Autowired
    public RegistrationRepositoryImpl(RestClient restClient, DynamoDbEnhancedClient dynamoDbEnhancedClient) {
        this.restClient = restClient;
        this.dynamoDbEnhancedClient = dynamoDbEnhancedClient;
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

    @Override
    public boolean saveRegistrationDetail(RegistrationDetail registrationDetail) {
        try {
            DynamoDbTable<RegistrationDetail> table = dynamoDbEnhancedClient
                    .table(Constants.REGISTRATION_DETAIL_TABLE, TableSchema.fromBean(RegistrationDetail.class));
            table.putItem(registrationDetail);
            return true;
        } catch (DynamoDbException e) {
            LOGGER.severe("Error saving RegistrationDetail: "+ e);
            return false;
        }
    }
}
