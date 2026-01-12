package com.example.demo.usuario.domain;

import java.util.List;

public interface UsuarioRepository {
    public void registrarUsuario(Usuario usuario);
    public List<Usuario> listarUsuarios();
}
