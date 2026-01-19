package com.example.demo.usuario.infraestructure.rest;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/usuarios")
public class UsuarioRestController {

//    private UsuarioUseCases usuarioUseCases;
//
//    public UsuarioRestController(UsuarioUseCases usuarioUseCases) {
//        this.usuarioUseCases = usuarioUseCases;
//    }

//    @GetMapping("api/usuarios")
//    public List<Usuario> listarUsuarios() {
//        return this.usuarioUseCases.listar();
//    }

    @GetMapping
    public String index(Authentication auth) {
        // todo, en el ejemplo solo sale auth.getName(), pero si no pongo .getClass() no me deja, ¿funciona?
        return "Email del token: " + auth.getClass().getName();
    }
}
