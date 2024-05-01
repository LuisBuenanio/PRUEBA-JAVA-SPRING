package com.pruebatecnica.backend.Controller;

import com.pruebatecnica.backend.Entity.User;
import com.pruebatecnica.backend.Entity.Role;
import com.pruebatecnica.backend.Service.SessionService;
import com.pruebatecnica.backend.Service.UserService;

import com.pruebatecnica.backend.Service.RoleService;
import com.pruebatecnica.backend.Util.JsonSchemaValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
public class UserController {
    private final JsonSchemaValidator jsonSchemaValidator;
    private final UserService userService;
    private final RoleService roleService;
    private final SessionService sessionService;

    @Autowired
    public UserController(JsonSchemaValidator jsonSchemaValidator, UserService userService, RoleService roleService, SessionService sessionService) {
        this.jsonSchemaValidator = jsonSchemaValidator;
        this.userService = userService;
        this.roleService = roleService;
        this.sessionService = sessionService;
    }

    @PostMapping("/users")
    public ResponseEntity<String> createUser(@RequestBody String requestBody) {
        if (!jsonSchemaValidator.validate(requestBody)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("JSON inválido");
        }

        User user = convertirJsonAUsuario(requestBody);

        if (userService.existeUsername(user.getUsername())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El nombre de usuario ya está en uso");
        }

        user.generarEmail();
        while (userService.existeEmail(user.getEmail())) {
            user.agregarNumeroEmailDuplicado();
        }

        if (!userService.validarRequisitosUsuario(user)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Requisitos de usuario no cumplidos");
        }
        Set<Role> roles = new HashSet<>();
        roles.add(roleService.buscarPorNombre("ROLE_USER"));
        roles.add(roleService.buscarPorNombre("ROLE_ADMIN"));
        user.setRoles(roles);

        User userGuardado = userService.registerUser(user);
        return ResponseEntity.ok("Usuario creado correctamente: " + userGuardado.getId());
    }

    private User convertirJsonAUsuario(String json) {
        // Implementa la lógica para convertir el JSON a objeto Usuario
    }

    @PostMapping("/users/login")
    public ResponseEntity<String> iniciarSesion(@RequestBody String requestBody) {
        // Validar JSON, obtener datos de inicio de sesión y validar credenciales
        // Supongamos que obtienes el usuarioId y el token después de validar las credenciales

        if (sessionService.existeSesionActiva(userId)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El usuario ya tiene una sesión activa");
        }

        // Registrar inicio de sesión
        sessionService.registrarInicioSesion(userId, token);

        return ResponseEntity.ok("Inicio de sesión exitoso");
    }

    @PostMapping("/users/logout")
    public ResponseEntity<String> cerrarSesion(@RequestParam Long usuarioId) {
        if (!sessionService.existeSesionActiva(usuarioId)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El usuario no tiene una sesión activa");
        }

        // Registrar cierre de sesión
        sessionService.registrarCierreSesion(usuarioId);

        return ResponseEntity.ok("Cierre de sesión exitoso");
    }
    @PostMapping("/cargar-desde-excel")
    public ResponseEntity<String> cargarUsuariosDesdeExcel(@RequestParam("file") MultipartFile file) {
        try {
            List<User> usuariosCargados = userService.cargarUsuariosDesdeExcel(file);
            return ResponseEntity.ok("Usuarios cargados correctamente desde Excel.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al cargar usuarios desde Excel: " + e.getMessage());
        }
    }

}

