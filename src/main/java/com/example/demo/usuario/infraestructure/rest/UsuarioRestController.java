package com.example.demo.usuario.infraestructure.rest;

import com.example.demo.context.security.JwtService;
import com.example.demo.usuario.application.UsuarioUseCases;
import com.example.demo.usuario.domain.Usuario;
import com.example.demo.usuario.infraestructure.db.UsuarioRepositoryMongo;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/usuarios")
public class UsuarioRestController {

    private UsuarioUseCases usuarioUseCases;
    private JwtService jwtService;

    public UsuarioRestController(JwtService jwtService) {
        this.usuarioUseCases = new UsuarioUseCases(new UsuarioRepositoryMongo());
        this.jwtService = jwtService;
    }

    @GetMapping
    public String index(Authentication auth) {
        return "Email del token: " + auth.getName();
    }

    @PostMapping("/registro")
    public String registro(@RequestBody Usuario usuario, Authentication auth){
        Boolean correcto = this.usuarioUseCases.registrarUsuario(usuario);
        if(correcto){
            return "Usuario registrado correctamente.";
        } else {
            return "No se ha podido realizar el registro.";
        }
    }

    @PostMapping("/login")
    public String login(@RequestBody Usuario usuario){
        Usuario login = this.usuarioUseCases.loginUsuario(usuario);
        if(login != null){
            return jwtService.generateToken(login.getEmail());
        } else {
            return "Usuario y/o contraseña incorrectos.";
        }
    }
}
