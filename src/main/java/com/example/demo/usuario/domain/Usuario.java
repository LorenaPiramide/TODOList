package com.example.demo.usuario.domain;

import com.example.demo.tarea.domain.Tarea;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
    private String email;
    private String password;
    private List<Tarea> creadas;
    private List<Tarea> asignadas;

    public Usuario(String email, String password) {
        this.email = email;
        this.password = password;
        this.creadas = new ArrayList<>();
        this.asignadas = new ArrayList<>();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Tarea> getCreadas() {
        return creadas;
    }

    public void setCreadas(List<Tarea> creadas) {
        this.creadas = creadas;
    }

    public List<Tarea> getAsignadas() {
        return asignadas;
    }

    public void setAsignadas(List<Tarea> asignadas) {
        this.asignadas = asignadas;
    }
}
