package com.example.demo.tarea.domain;

import com.example.demo.context.prioridad.Prioridad;
import com.example.demo.usuario.domain.Usuario;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Tarea {
    private int id;
    private String texto;
    private Prioridad prioridad;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaFinalizacion;
    private boolean estaCompletada;
    private Usuario propietario;
    //private Usuario asignador; Creo que no hace falta. El creador es el único que puede asignar usuarios a la tarea.
    private List<Usuario> usuariosAsignados;

    public Tarea(int id, String texto, Prioridad prioridad, LocalDateTime fechaCreacion, LocalDateTime fechaFinalizacion, boolean estaCompletada, Usuario propietario) {
        this.id = id;
        this.texto = texto;
        this.prioridad = prioridad;
        this.fechaCreacion = fechaCreacion;
        this.fechaFinalizacion = fechaFinalizacion;
        this.estaCompletada = estaCompletada;
        this.propietario = propietario;
        this.usuariosAsignados = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Prioridad getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(Prioridad prioridad) {
        this.prioridad = prioridad;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDateTime getFechaFinalizacion() {
        return fechaFinalizacion;
    }

    public void setFechaFinalizacion(LocalDateTime fechaFinalizacion) {
        this.fechaFinalizacion = fechaFinalizacion;
    }

    public boolean isEstaCompletada() {
        return estaCompletada;
    }

    public void setEstaCompletada(boolean estaCompletada) {
        this.estaCompletada = estaCompletada;
    }

    public Usuario getPropietario() {
        return propietario;
    }

    public void setPropietario(Usuario propietario) {
        this.propietario = propietario;
    }

    public List<Usuario> getUsuariosAsignados() {
        return usuariosAsignados;
    }

    public void setUsuariosAsignados(List<Usuario> usuariosAsignados) {
        this.usuariosAsignados = usuariosAsignados;
    }
}
