package com.repo.registration.repository.impl;

import com.repo.registration.common.dto.request.RegistrationConfig;
import com.repo.registration.common.dto.request.RegistrationRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;

import java.net.URI;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@ExtendWith(MockitoExtension.class)
class RegistrationRepositoryImplTest {

    @Mock
    private RestClient restClient;

    // Mocks for each step of the RestClient fluent chain
    @Mock
    private RestClient.RequestBodyUriSpec requestBodyUriSpec;

    @Mock
    private RestClient.RequestBodySpec requestBodySpec;

    @Mock
    private RestClient.ResponseSpec responseSpec;

    @InjectMocks
    private RegistrationRepositoryImpl registrationRepository;

    @Test
    void registerWebHookForRepo_withCorrectData_shouldRegisterRepo() {
        // Arrange
        RegistrationRequest registrationRequest = new RegistrationRequest(
                "web", true, List.of("push"), new RegistrationConfig("https://example.com/webhook", "json", "0")
        );
        ResponseEntity<Void> response = new ResponseEntity<>(HttpStatus.OK);

        Mockito.when(restClient.post()).thenReturn(requestBodyUriSpec);
        Mockito.when(requestBodyUriSpec.uri(Mockito.any(URI.class))).thenReturn(requestBodySpec);
        Mockito.when(requestBodySpec.header(Mockito.anyString(), Mockito.anyString())).thenReturn(requestBodySpec);
        Mockito.doReturn(requestBodySpec).when(requestBodySpec).body(Mockito.any(Object.class));
        Mockito.when(requestBodySpec.retrieve()).thenReturn(responseSpec);
        Mockito.when(responseSpec.toBodilessEntity()).thenReturn(response);

        // Act
        int status = registrationRepository.registerWebHookForRepo(
                registrationRequest, "some-owner", "some-repo", "some-token"
        );

        // Assert
        assertThat(status, is(HttpStatus.OK.value()));
    }
}
