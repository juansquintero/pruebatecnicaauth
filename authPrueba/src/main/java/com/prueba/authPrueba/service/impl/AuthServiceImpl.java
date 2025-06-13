package com.prueba.authPrueba.service.impl;

import com.prueba.authPrueba.client.DummyJsonClient;
import com.prueba.authPrueba.dto.LoginRequest;
import com.prueba.authPrueba.dto.LoginResponse;
import com.prueba.authPrueba.dto.UserDto;
import com.prueba.authPrueba.model.LoginLog;
import com.prueba.authPrueba.repository.LoginLogRepository;
import com.prueba.authPrueba.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

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
        LoginResponse response = dummyJsonClient.login(loginRequest);
        
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
        String cookie = "accessToken=" + token;
        
        return dummyJsonClient.getAuthenticatedUser(cookie);
    }
    
    @Override
    public UserDto getAuthenticatedUserByUsername(String username) {
        List<LoginLog> loginLogs = loginLogRepository.findByUsername(
                username, 
                PageRequest.of(0, 1, Sort.by(Sort.Direction.DESC, "loginTime"))
        );
        
        if (loginLogs.isEmpty()) {
            throw new RuntimeException("No login found for username: " + username);
        }
        
        String accessToken = loginLogs.get(0).getAccessToken();
        
        return getAuthenticatedUser(accessToken);
    }
} 