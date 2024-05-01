package com.pruebatecnica.backend.Repository;


import com.pruebatecnica.backend.Entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository extends JpaRepository<Session, Long> {
    Session findByUsuarioIdAndCierreIsNull(Long userId);
}
