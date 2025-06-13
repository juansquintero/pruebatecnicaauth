package com.prueba.authPrueba.repository;

import com.prueba.authPrueba.model.LoginLog;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LoginLogRepository extends JpaRepository<LoginLog, UUID> {
    List<LoginLog> findByUsername(String username, Pageable pageable);
} 