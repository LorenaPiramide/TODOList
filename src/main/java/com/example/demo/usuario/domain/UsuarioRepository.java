package com.example.demo.usuario.domain;

public interface UsuarioRepository {
    void reset();
    boolean registrarUsuario(Usuario usuario);
    Usuario loginUsuario(Usuario usuario);
}
