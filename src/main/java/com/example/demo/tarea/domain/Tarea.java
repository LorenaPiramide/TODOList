package com.example.demo.tarea.domain;

import com.example.demo.context.prioridad.Prioridad;
import com.example.demo.usuario.domain.Usuario;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Tarea {
    private String id; // los ids en Mongo son Strings, no ints
    private String texto;
    private Prioridad prioridad;
    private Date fechaCreacion;
    private Date fechaFinalizacion;
    private boolean estaCompletada;
    private Usuario propietario;
    private List<Usuario> usuariosAsignados;

    public Tarea(String id, String texto, Prioridad prioridad, Date fechaCreacion, Date fechaFinalizacion, boolean estaCompletada, Usuario propietario) {
        this.id = id;
        this.texto = texto;
        this.prioridad = prioridad;
        this.fechaCreacion = fechaCreacion;
        this.fechaFinalizacion = fechaFinalizacion;
        this.estaCompletada = estaCompletada;
        this.propietario = propietario;
        this.usuariosAsignados = new ArrayList<>();
    }

    public Tarea() {
        this.usuariosAsignados = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public Date getFechaFinalizacion() {
        return fechaFinalizacion;
    }

    public boolean isEstaCompletada() {
        return estaCompletada;
    }

    public void setEstaCompletada(boolean estaCompletada) {
        this.estaCompletada = estaCompletada;
    }

    public void setUsuariosAsignados(List<Usuario> usuariosAsignados) {
        this.usuariosAsignados = usuariosAsignados;
    }
}
