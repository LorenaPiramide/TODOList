package com.example.demo.tarea.domain;

import com.example.demo.usuario.domain.Usuario;

import java.util.List;

public interface TareaRepository {
    public void crearTarea(Tarea tarea, Usuario usuario);
    public void asignarTarea(Tarea tarea, Usuario usuario);
    public List<Tarea> listarTareasUsuario(Usuario usuario);
}
