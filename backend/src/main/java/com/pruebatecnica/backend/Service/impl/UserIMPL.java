package com.pruebatecnica.backend.Service.impl;

import com.pruebatecnica.backend.Dto.UserDTO;
import com.pruebatecnica.backend.Dto.LoginDTO;
import com.pruebatecnica.backend.Entity.User;
import com.pruebatecnica.backend.Repo.UserRepo;
import com.pruebatecnica.backend.Service.UserService;
import com.pruebatecnica.backend.response.LoginMesage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.Optional;

@Service

public class UserIMPL implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public String addUser(UserDTO userDTO) {

        User user = new User(

                userDTO.getUserid(),
                userDTO.getUsername(),
                userDTO.getEmail(),

                this.passwordEncoder.encode(userDTO.getPassword())
        );

        userRepo.save(user);

        return user.getUsername();
    }
    UserDTO userDTO;

    @Override
    public LoginMesage  loginUser(LoginDTO loginDTO) {
        String msg = "";
        User user1 = userRepo.findByEmail(loginDTO.getEmail());
        if (user1 != null) {
            String password = loginDTO.getPassword();
            String encodedPassword = user1.getPassword();
            Boolean isPwdRight = passwordEncoder.matches(password, encodedPassword);
            if (isPwdRight) {
                Optional<User> user = userRepo.findOneByEmailAndPassword(loginDTO.getEmail(), encodedPassword);
                if (user.isPresent()) {
                    return new LoginMesage("Login Correcto", true);
                } else {
                    return new LoginMesage("Login Fallido", false);
                }
            } else {

                return new LoginMesage("Contraseña Incorrecta", false);
            }
        }else {
            return new LoginMesage("Email no existe", false);
        }


    }

}