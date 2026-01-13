package com.example.demo.usuario.domain;

import java.util.List;

public interface UsuarioRepository {
    void registrarUsuario(Usuario usuario);
    List<Usuario> listarUsuarios();
}
