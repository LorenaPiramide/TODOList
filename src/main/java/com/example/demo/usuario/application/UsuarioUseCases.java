package com.example.demo.usuario.application;

import com.example.demo.usuario.domain.Usuario;
import com.example.demo.usuario.domain.UsuarioRepository;

import java.util.List;

public class UsuarioUseCases {
    private UsuarioRepository usuarioRepository;

    public UsuarioUseCases(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> listar() {
        return this.usuarioRepository.listarUsuarios();
    }

    public void registrarUsuario(Usuario usuario) {
        this.usuarioRepository.registrarUsuario(usuario);
    }
}
