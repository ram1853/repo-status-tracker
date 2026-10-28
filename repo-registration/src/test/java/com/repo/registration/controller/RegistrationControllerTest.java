package com.repo.registration.controller;

import com.repo.registration.common.dto.response.RegistrationResponse;
import com.repo.registration.service.IRegistrationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@ExtendWith(MockitoExtension.class)
class RegistrationControllerTest {

    @Mock
    private IRegistrationService registrationService;

    @InjectMocks
    private RegistrationController registrationController;

    @Test
    void registerWebHookForRepo_withSuccessfulRegistrationAndSavedState_shouldReturnOkResponse(){
        //Arrange
        Mockito.when(registrationService.registerWebHookForRepo(Mockito.any(), Mockito.any(),
                Mockito.any(), Mockito.any())).thenReturn(HttpStatus.CREATED.value());
        Mockito.when(registrationService.saveRegistrationDetail(Mockito.any(), Mockito.any(),
                Mockito.any())).thenReturn(true);

        //Act
        ResponseEntity<RegistrationResponse> response = registrationController.registerWebHookForRepo(Mockito.any(), Mockito.any(),
                Mockito.any(), Mockito.any());

        //Assert
        Mockito.verify(registrationService, Mockito.times(1)).registerWebHookForRepo(Mockito.any(), Mockito.any(),
                Mockito.any(), Mockito.any());
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody().message(), is("Repo successfully registered"));
    }

    @Test
    void registerWebHookForRepo_withSuccessfulRegistrationAndUnsavedState_shouldReturnPartialSuccessResponse(){
        //Arrange
        Mockito.when(registrationService.registerWebHookForRepo(Mockito.any(), Mockito.any(),
                Mockito.any(), Mockito.any())).thenReturn(HttpStatus.CREATED.value());
        Mockito.when(registrationService.saveRegistrationDetail(Mockito.any(), Mockito.any(),
                Mockito.any())).thenReturn(false);

        //Act
        ResponseEntity<RegistrationResponse> response = registrationController.registerWebHookForRepo(Mockito.any(), Mockito.any(),
                Mockito.any(), Mockito.any());

        //Assert
        Mockito.verify(registrationService, Mockito.times(1)).registerWebHookForRepo(Mockito.any(), Mockito.any(),
                Mockito.any(), Mockito.any());
        assertThat(response.getStatusCode(), is(HttpStatus.MULTI_STATUS));
        assertThat(response.getBody().message(), is("Repo registered, but state is not saved - contact Admin to reconcile state"));
    }

    @Test
    void registerWebHookForRepo_withUnSuccessfulRegistration_shouldReturnNonOkResponse(){
        //Arrange
        Mockito.when(registrationService.registerWebHookForRepo(Mockito.any(), Mockito.any(),
                Mockito.any(), Mockito.any())).thenReturn(HttpStatus.UNPROCESSABLE_CONTENT.value());

        //Act
        ResponseEntity<RegistrationResponse> response = registrationController.registerWebHookForRepo(Mockito.any(), Mockito.any(),
                Mockito.any(), Mockito.any());

        //Assert
        Mockito.verify(registrationService, Mockito.times(1)).registerWebHookForRepo(Mockito.any(), Mockito.any(),
                Mockito.any(), Mockito.any());
        assertThat(response.getStatusCode(), is(HttpStatus.UNPROCESSABLE_CONTENT));
        assertThat(response.getBody().message(), is("Repo registration failed"));
    }
}