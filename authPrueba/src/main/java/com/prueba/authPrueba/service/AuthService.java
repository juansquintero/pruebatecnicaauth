package com.prueba.authPrueba.service;

import com.prueba.authPrueba.dto.LoginRequest;
import com.prueba.authPrueba.dto.LoginResponse;
import com.prueba.authPrueba.dto.UserDto;

public interface AuthService {
    LoginResponse login(LoginRequest loginRequest);
    UserDto getAuthenticatedUser(String token);
    UserDto getAuthenticatedUserByUsername(String username);
} 