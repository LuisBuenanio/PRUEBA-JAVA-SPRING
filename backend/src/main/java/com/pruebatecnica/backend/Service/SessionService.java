package com.pruebatecnica.backend.Service;


import com.pruebatecnica.backend.Entity.Session;
import com.pruebatecnica.backend.Repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SessionService {
    private final SessionRepository sessionRepository;

    @Autowired
    public SessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public boolean existeSesionActiva(Long usuarioId) {
        Session sessionActiva = sessionRepository.findByUsuarioIdAndCierreIsNull(usuarioId);
        return sessionActiva != null;
    }

    public void registrarInicioSesion(Long usuarioId, String token) {
        Session session = new Session();
        session.setUsuarioId(usuarioId);
        session.setToken(token);
        session.setInicio(LocalDateTime.now());
        sessionRepository.save(session);
    }

    public void registrarCierreSesion(Long usuarioId) {
        Session sesionActiva = sessionRepository.findByUsuarioIdAndCierreIsNull(usuarioId);
        if (sesionActiva != null) {
            sessionActiva.setCierre(LocalDateTime.now());
            sessionRepository.save(sesionActiva);
        }
    }
}
