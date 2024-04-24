package com.pruebatecnica.backend.Service;
import com.pruebatecnica.backend.Dto.UserDTO;
import com.pruebatecnica.backend.Dto.LoginDTO;
import com.pruebatecnica.backend.response.LoginMesage;

public interface UserService {
    String addUser(UserDTO userDTO);

    LoginMesage loginUser(LoginDTO loginDTO);

}