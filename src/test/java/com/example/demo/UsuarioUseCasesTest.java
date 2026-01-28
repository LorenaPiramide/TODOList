package com.example.demo;

import com.example.demo.usuario.application.UsuarioUseCases;
import com.example.demo.usuario.domain.Usuario;
import com.example.demo.usuario.infraestructure.db.UsuarioRepositoryMongo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UsuarioUseCasesTest {
    private UsuarioUseCases usuarioUseCases;

    public UsuarioUseCasesTest() {
        this.usuarioUseCases = new UsuarioUseCases(new UsuarioRepositoryMongo());
    }

    @Test
    void loginCorrecto() {
        this.usuarioUseCases.registrarUsuario(new Usuario("test@test", "testpass"));

        Usuario resultado = usuarioUseCases.loginUsuario(new Usuario("test@test", "testpass"));

        // Solo se comprueba el email, ya que login usuario comprueba la contraseña y si corresponde a ese email
        assertNotNull(resultado, "No puede ser null");
        assertEquals("test@test", resultado.getEmail(), "El email debe ser correcto.");
    }

    @Test
    void loginIncorrecto() {
        this.usuarioUseCases.registrarUsuario(new Usuario("test@test", "testpass"));

        Usuario resultado = usuarioUseCases.loginUsuario(new Usuario("test@test", "errorpass"));

        assertNull(resultado, "El test debe fallar por la contraseña.");
    }

    @Test
    void registrarUsuarioTest() {
        Usuario usuario = new Usuario("nuevo@test", "pass123");

        boolean registrado = this.usuarioUseCases.registrarUsuario(usuario);
        assertTrue(registrado, "El usuario debe registrarse correctamente");

        Usuario login = this.usuarioUseCases.loginUsuario(new Usuario("nuevo@test", "pass123"));
        assertNotNull(login, "El usuario registrado debe poder hacer login");
        assertEquals("nuevo@test", login.getEmail(), "El email del usuario debe coincidir");
    }

    @AfterEach
    void reset() {
        this.usuarioUseCases.reset();
    }
}
