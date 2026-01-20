package com.example.demo.usuario.application;

import com.example.demo.usuario.domain.Usuario;
import com.example.demo.usuario.domain.UsuarioRepository;
import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;

public class UsuarioUseCases {
    private UsuarioRepository usuarioRepository;

    public UsuarioUseCases(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

//    public List<Usuario> listar() {
//        return this.usuarioRepository.listarUsuarios();
//    }

    public boolean registrarUsuario(Usuario usuario) {
        String password = Hashing.sha256().hashString(usuario.getPassword(), StandardCharsets.UTF_8).toString();
        // Ciframos al usuario para que no se muestre la contraseña
        Usuario cifrado = new Usuario(usuario.getEmail(), password);
        // Devolvemos al usuario con la contraseña cifrada
        return this.usuarioRepository.registrarUsuario(cifrado);
    }

    public Usuario loginUsuario(Usuario usuario) {
        Usuario usuarioDB = this.usuarioRepository.loginUsuario(usuario);
        if (usuarioDB == null) return null;
        // Pasamos la contraseña cifrada a String
        String password = Hashing.sha256().hashString(usuario.getPassword(), StandardCharsets.UTF_8).toString();
        // Si coincide, devolvemos al usuario
        if (usuarioDB.getPassword().equals(password)) {
            return usuario;
        } else return null;
    }
}
