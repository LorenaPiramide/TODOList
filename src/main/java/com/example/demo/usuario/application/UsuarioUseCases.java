package com.example.demo.usuario.application;

import com.example.demo.usuario.domain.Usuario;
import com.example.demo.usuario.domain.UsuarioRepository;

public class UsuarioUseCases {
    private UsuarioRepository usuarioRepository;

    public UsuarioUseCases(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

//    public List<Usuario> listar() {
//        return this.usuarioRepository.listarUsuarios();
//    }

    public boolean registrarUsuario(Usuario usuario) {
        return this.usuarioRepository.registrarUsuario(usuario);
    }

    public Usuario loginUsuario(Usuario usuario) {
        return this.usuarioRepository.loginUsuario(usuario);
    }
}
