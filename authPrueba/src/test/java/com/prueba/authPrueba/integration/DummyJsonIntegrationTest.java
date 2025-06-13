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
        LoginRequest loginRequest = new LoginRequest("emilys", "emilyspass");

        LoginResponse response = dummyJsonClient.login(loginRequest);

        assertNotNull(response);
        assertEquals("emilys", response.getUsername());
        assertNotNull(response.getAccessToken());
        assertNotNull(response.getRefreshToken());

        UserDto user = dummyJsonClient.getAuthenticatedUser("accessToken=" + response.getAccessToken());
        
        assertNotNull(user);
        assertEquals("emilys", user.getUsername());
        assertNotNull(user.getEmail());
    }
} 