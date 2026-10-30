package com.repo.registration.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class HealthControllerTest {

    @MockitoBean
    private DynamoDbEnhancedClient dynamoDbEnhancedClient;

    @Autowired
    private RestClient restClient;

    @Test
    void ping_shouldReturnOkResponse(){
        //Act
        ResponseEntity<String> threadResponse = restClient.get()
                .uri(UriComponentsBuilder
                        .newInstance().scheme("http")
                        .host("localhost")
                        .port(8080)
                        .path("/api/v1/registration/ping").build().toUri())
                .retrieve().toEntity(String.class);

        //Assert
        assertThat(threadResponse.getBody().equals("ok"), is(true));
    }

}