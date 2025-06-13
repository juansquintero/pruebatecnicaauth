package com.prueba.authPrueba.service.impl;

import com.prueba.authPrueba.client.DummyJsonClient;
import com.prueba.authPrueba.dto.LoginRequest;
import com.prueba.authPrueba.dto.LoginResponse;
import com.prueba.authPrueba.dto.UserDto;
import com.prueba.authPrueba.model.LoginLog;
import com.prueba.authPrueba.repository.LoginLogRepository;
import com.prueba.authPrueba.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final DummyJsonClient dummyJsonClient;
    private final LoginLogRepository loginLogRepository;

    @Autowired
    public AuthServiceImpl(DummyJsonClient dummyJsonClient, LoginLogRepository loginLogRepository) {
        this.dummyJsonClient = dummyJsonClient;
        this.loginLogRepository = loginLogRepository;
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        // Call DummyJSON API to authenticate
        LoginResponse response = dummyJsonClient.login(loginRequest);
        
        // Log successful login
        if (response != null && response.getAccessToken() != null) {
            LoginLog loginLog = new LoginLog(
                    loginRequest.getUsername(),
                    response.getAccessToken(),
                    response.getRefreshToken()
            );
            loginLogRepository.save(loginLog);
        }
        
        return response;
    }

    @Override
    public UserDto getAuthenticatedUser(String token) {
        // Format token as cookie
        String cookie = "accessToken=" + token;
        
        // Call DummyJSON API to get authenticated user
        return dummyJsonClient.getAuthenticatedUser(cookie);
    }
} 