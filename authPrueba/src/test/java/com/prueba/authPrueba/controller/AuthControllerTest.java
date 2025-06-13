package com.prueba.authPrueba.controller;

import com.prueba.authPrueba.dto.LoginRequest;
import com.prueba.authPrueba.dto.LoginResponse;
import com.prueba.authPrueba.dto.UserDto;
import com.prueba.authPrueba.service.AuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @Test
    void testLoginWithEmilysCredentials() throws Exception {
        // Arrange
        LoginResponse mockResponse = new LoginResponse();
        mockResponse.setId(1);
        mockResponse.setUsername("emilys");
        mockResponse.setEmail("emily@example.com");
        mockResponse.setAccessToken("mock-access-token-for-emilys");
        mockResponse.setRefreshToken("mock-refresh-token-for-emilys");

        when(authService.login(any(LoginRequest.class))).thenReturn(mockResponse);

        // Act & Assert
        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"emilys\",\"password\":\"emilyspass\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("emilys"))
                .andExpect(jsonPath("$.accessToken").value("mock-access-token-for-emilys"))
                .andExpect(jsonPath("$.refreshToken").value("mock-refresh-token-for-emilys"));
    }

    @Test
    void testGetAuthenticatedUser() throws Exception {
        // Arrange
        UserDto mockUser = new UserDto();
        mockUser.setId(1);
        mockUser.setUsername("emilys");
        mockUser.setEmail("emily@example.com");
        mockUser.setFirstName("Emily");
        mockUser.setLastName("Smith");

        when(authService.getAuthenticatedUser(any(String.class))).thenReturn(mockUser);

        // Act & Assert
        mockMvc.perform(get("/api/auth/me")
                .header("Authorization", "mock-access-token-for-emilys"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("emilys"))
                .andExpect(jsonPath("$.firstName").value("Emily"))
                .andExpect(jsonPath("$.lastName").value("Smith"));
    }
} 