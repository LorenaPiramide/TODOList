package com.example.demo.usuario.infraestructure.rest;

import com.example.demo.context.security.JwtService;
import com.example.demo.usuario.application.UsuarioUseCases;
import com.example.demo.usuario.domain.Usuario;
import com.example.demo.usuario.infraestructure.db.UsuarioRepositoryMongo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthRestController {

    private final JwtService jwtService;
    private UsuarioUseCases usuarioUseCases;

    public AuthRestController(JwtService jwtService) {
        this.jwtService = jwtService;
        this.usuarioUseCases = new UsuarioUseCases(new UsuarioRepositoryMongo());
    }

    @PostMapping("/api/usuarios/registro")
    public String registro(@RequestBody Usuario usuario) {
        boolean esCorrecto = this.usuarioUseCases.registrarUsuario(usuario);
        if (esCorrecto) {
            return "Registro correcto.";
        } else {
            return "Registro incorrecto";
        }
    }

    @PostMapping("/api/usuarios/login")
    public String login(@RequestBody Usuario usuario) {
        Usuario login = this.usuarioUseCases.loginUsuario(usuario);
        if (login != null) {
            return jwtService.generateToken(login.getEmail());
        } else {
            return "Usuario y/o contraseña incorrectos.";
        }
    }

}
