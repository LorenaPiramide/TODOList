package com.example.demo.usuario.infraestructure.rest;

import com.example.demo.usuario.application.UsuarioUseCases;
import com.example.demo.usuario.domain.Usuario;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UsuarioRestController {

    private UsuarioUseCases usuarioUseCases;

    public UsuarioRestController(UsuarioUseCases usuarioUseCases) {
        this.usuarioUseCases = usuarioUseCases;
    }

    @GetMapping("api/usuarios")
    public List<Usuario> listarUsuarios() {
        return this.usuarioUseCases.listar();
    }
    
    @PostMapping("/api/usuarios")
    public void registrarUsuario(Usuario usuario) {
        this.usuarioUseCases.registrarUsuario(usuario);
    }
}
