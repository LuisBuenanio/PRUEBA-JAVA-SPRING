package com.pruebatecnica.backend.Principal;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PrincipalController {

    @PostMapping(value = "principal")
    public String welcome()
    {
        return "Bienvenido";
    }
}
