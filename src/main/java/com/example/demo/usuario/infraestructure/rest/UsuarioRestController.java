package com.example.demo.usuario.infraestructure.rest;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/usuarios")
public class UsuarioRestController {
    @GetMapping
    public String index(Authentication auth) {
        return "Email del token: " + auth.getName();
    }
}
