package com.prueba.authPrueba.integration;

import com.prueba.authPrueba.client.DummyJsonClient;
import com.prueba.authPrueba.dto.LoginRequest;
import com.prueba.authPrueba.dto.LoginResponse;
import com.prueba.authPrueba.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class DummyJsonIntegrationTest {

    @Autowired
    private DummyJsonClient dummyJsonClient;

    @Test
    void testLoginWithEmilysCredentials() {
        // Arrange
        LoginRequest loginRequest = new LoginRequest("emilys", "emilyspass");

        // Act
        LoginResponse response = dummyJsonClient.login(loginRequest);

        // Assert
        assertNotNull(response);
        assertEquals("emilys", response.getUsername());
        assertNotNull(response.getAccessToken());
        assertNotNull(response.getRefreshToken());
        System.out.println("AccessToken: " + response.getAccessToken());
        System.out.println("RefreshToken: " + response.getRefreshToken());

        // Test /auth/me endpoint with the token
        UserDto user = dummyJsonClient.getAuthenticatedUser("accessToken=" + response.getAccessToken());
        
        // Assert user data
        assertNotNull(user);
        assertEquals("emilys", user.getUsername());
        assertNotNull(user.getEmail());
        System.out.println("User ID: " + user.getId());
        System.out.println("Username: " + user.getUsername());
        System.out.println("Email: " + user.getEmail());
    }
} 