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
class ThreadControllerTest {

    @MockitoBean
    private DynamoDbEnhancedClient dynamoDbEnhancedClient;

    @Autowired
    private RestClient restClient;

    @Test
    void currentThread_shouldReturnAVirtualThreadInstance(){
        //Act
        ResponseEntity<String> threadResponse = restClient.get()
                .uri(UriComponentsBuilder
                        .newInstance().scheme("http")
                        .host("localhost")
                        .port(8080)
                        .path("/api/v1/ping").build().toUri())
                .retrieve().toEntity(String.class);

        //Assert
        assertThat(threadResponse.getBody().startsWith("VirtualThread"), is(true));
    }

}