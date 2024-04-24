package com.pruebatecnica.backend.UserController;
import com.pruebatecnica.backend.Dto.UserDTO;
import com.pruebatecnica.backend.Dto.LoginDTO;
import com.pruebatecnica.backend.Service.UserService;
import com.pruebatecnica.backend.response.LoginMesage;
import com.pruebatecnica.backend.response.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@CrossOrigin
@RequestMapping("api/users")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping(path = "/save")
    public String saveUser(@RequestBody UserDTO userDTO)
    {
        String id = userService.addUser(userDTO);
        return id;
    }
    @PostMapping(path = "/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginDTO loginDTO)
    {
        LoginMesage LoginMesage  = userService.loginUser(loginDTO);
        return ResponseEntity.ok(LoginMesage);
    }
}
