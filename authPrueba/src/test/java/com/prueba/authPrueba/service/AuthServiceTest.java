package com.prueba.authPrueba.service;

import com.prueba.authPrueba.client.DummyJsonClient;
import com.prueba.authPrueba.dto.LoginRequest;
import com.prueba.authPrueba.dto.LoginResponse;
import com.prueba.authPrueba.dto.UserDto;
import com.prueba.authPrueba.model.LoginLog;
import com.prueba.authPrueba.repository.LoginLogRepository;
import com.prueba.authPrueba.service.impl.AuthServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    @Mock
    private DummyJsonClient dummyJsonClient;

    @Mock
    private LoginLogRepository loginLogRepository;

    @Captor
    private ArgumentCaptor<LoginLog> loginLogCaptor;

    private AuthService authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        authService = new AuthServiceImpl(dummyJsonClient, loginLogRepository);
    }

    @Test
    void testSuccessfulLogin() {
        // Arrange
        LoginRequest loginRequest = new LoginRequest("emilys", "emilyspass");
        LoginResponse mockResponse = new LoginResponse();
        mockResponse.setId(1);
        mockResponse.setUsername("emilys");
        mockResponse.setAccessToken("mock-access-token");
        mockResponse.setRefreshToken("mock-refresh-token");

        when(dummyJsonClient.login(any(LoginRequest.class))).thenReturn(mockResponse);

        // Act
        LoginResponse result = authService.login(loginRequest);

        // Assert
        assertEquals("emilys", result.getUsername());
        assertEquals("mock-access-token", result.getAccessToken());
        assertEquals("mock-refresh-token", result.getRefreshToken());

        // Verify login was logged
        verify(loginLogRepository).save(loginLogCaptor.capture());
        LoginLog capturedLog = loginLogCaptor.getValue();
        assertEquals("emilys", capturedLog.getUsername());
        assertEquals("mock-access-token", capturedLog.getAccessToken());
        assertEquals("mock-refresh-token", capturedLog.getRefreshToken());
    }
}

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class AuthServiceIntegrationTest {

    @MockBean
    private DummyJsonClient dummyJsonClient;

    @Autowired
    private LoginLogRepository loginLogRepository;

    @Autowired
    private AuthService authService;

    @Test
    void testLoginSavesToDatabase() {
        // Arrange
        LoginRequest loginRequest = new LoginRequest("testuser", "password123");
        LoginResponse mockResponse = new LoginResponse();
        mockResponse.setId(1);
        mockResponse.setUsername("testuser");
        mockResponse.setAccessToken("test-access-token-123");
        mockResponse.setRefreshToken("test-refresh-token-123");

        when(dummyJsonClient.login(any(LoginRequest.class))).thenReturn(mockResponse);

        // Clear any existing logs
        loginLogRepository.deleteAll();

        // Act
        LoginResponse result = authService.login(loginRequest);

        // Assert
        assertEquals("testuser", result.getUsername());
        
        // Verify data was saved to database
        List<LoginLog> logs = loginLogRepository.findAll();
        assertEquals(1, logs.size());
        
        LoginLog savedLog = logs.get(0);
        assertNotNull(savedLog.getId());
        assertEquals("testuser", savedLog.getUsername());
        assertEquals("test-access-token-123", savedLog.getAccessToken());
        assertEquals("test-refresh-token-123", savedLog.getRefreshToken());
        assertNotNull(savedLog.getLoginTime());
    }
} 