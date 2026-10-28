package com.repo.registration.service.impl;

import com.repo.registration.common.dto.request.RegistrationConfig;
import com.repo.registration.common.dto.request.RegistrationRequest;
import com.repo.registration.repository.IRegistrationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@ExtendWith(MockitoExtension.class)
class RegistrationServiceImplTest {

    @Mock
    private IRegistrationRepository registrationRepository;

    @InjectMocks
    private RegistrationServiceImpl registrationService;

    @Test
    void registerWebHookForRepo_shouldCallRepoMethod(){
        //Arrange
        Mockito.when(registrationRepository.registerWebHookForRepo(Mockito.any(), Mockito.any(),
                Mockito.any(), Mockito.any())).thenReturn(HttpStatus.OK.value());

        //Act
        int result = registrationService.registerWebHookForRepo(Mockito.any(), Mockito.any(),
                Mockito.any(), Mockito.any());

        //Assert
        Mockito.verify(registrationRepository, Mockito.times(1)).registerWebHookForRepo(Mockito.any(), Mockito.any(),
                Mockito.any(), Mockito.any());
        assertThat(result, is(HttpStatus.OK.value()));
    }

    @Test
    void saveRegistrationDetail_shouldCallRepoMethod(){
        //Arrange
        RegistrationConfig registrationConfig = new RegistrationConfig("test-url", "json", "false");
        RegistrationRequest registrationRequest = new RegistrationRequest("test-name", true, List.of("push"), registrationConfig);
        Mockito.when(registrationRepository.saveRegistrationDetail(Mockito.any())).thenReturn(true);

        //Act
        boolean result = registrationService.saveRegistrationDetail(registrationRequest, "test-owner", "test-repo");

        //Assert
        Mockito.verify(registrationRepository, Mockito.times(1)).saveRegistrationDetail(Mockito.any());
        assertThat(result, is(true));
    }

}