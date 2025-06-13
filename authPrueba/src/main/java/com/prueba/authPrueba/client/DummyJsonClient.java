package com.prueba.authPrueba.client;

import com.prueba.authPrueba.dto.LoginRequest;
import com.prueba.authPrueba.dto.LoginResponse;
import com.prueba.authPrueba.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;
import java.util.Map;

@FeignClient(name = "dummyJson", url = "${dummyjson.api.url}")
public interface DummyJsonClient {

    @PostMapping("/auth/login")
    LoginResponse login(@RequestBody LoginRequest loginRequest);

    @GetMapping("/auth/me")
    UserDto getAuthenticatedUser(@RequestHeader("Cookie") String cookie);

    @PostMapping("/auth/refresh-token")
    LoginResponse refreshToken(@RequestHeader("Cookie") String refreshTokenCookie);

    @GetMapping("/users")
    Map<String, List<UserDto>> getUsers();
} 