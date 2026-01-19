package com.example.demo.usuario.domain;

import java.util.List;

public interface UsuarioRepository {
    boolean registrarUsuario(Usuario usuario);
    Usuario loginUsuario(Usuario usuario);
//    List<Usuario> listarUsuarios();
}
