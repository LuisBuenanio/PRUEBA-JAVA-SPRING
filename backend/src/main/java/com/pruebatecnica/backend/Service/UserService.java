package com.pruebatecnica.backend.Service;

import com.pruebatecnica.backend.Entity.User;
import com.pruebatecnica.backend.Repository.UserRepository;
import com.pruebatecnica.backend.Util.ExcelUtil;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;


@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean existeUsername(String username) {
        return userRepository.findByUsername(username) != null;
    }

    public boolean existeEmail(String email) {
        return userRepository.findByEmail(email) != null;
    }

    public boolean validarRequisitosUsuario(User user) {
        // Implementa la lógica para validar los requisitos de usuario
        // Retorna true si cumple todos los requisitos, false de lo contrario
        return 0;
    }

    public User registerUser(User user) {
        // Lógica para generar el correo y validar datos antes de guardar
        // Ejemplo de generación de correo
        String emailGenerado = user.getFirstname().toLowerCase() + user.getLastname().toLowerCase() + "@mail.com";
        user.setEmail(emailGenerado);

        // Más validaciones y lógica aquí

        return userRepository.save(user);
    }

    public List<User> cargarUsuariosDesdeExcel(MultipartFile file) throws IOException {
        // Lógica para leer el archivo Excel y procesar los usuarios
        List<User> usuarios = ExcelUtil.leerUsuariosDesdeExcel(file.getInputStream());
        return userRepository.saveAll(usuarios);
    }


}
