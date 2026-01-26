package com.example.demo.tarea.application;

import com.example.demo.tarea.domain.Tarea;
import com.example.demo.tarea.domain.TareaRepository;
import com.example.demo.usuario.domain.Usuario;

import java.util.List;

public class TareaUseCases {
    private TareaRepository tareaRepository;

    public TareaUseCases(TareaRepository tareaRepository) {
        this.tareaRepository = tareaRepository;
    }

    public void reset() {
        this.tareaRepository.reset();
    }

    public void crearTarea(Tarea tarea, Usuario usuario) {
        this.tareaRepository.crearTarea(tarea, usuario);
    }

    public List<Tarea> listarTareasUsuario(Usuario usuario) {
        return this.tareaRepository.listarTareasUsuario(usuario);
    }

    public void asignarTarea(Tarea tarea, Usuario propietario, Usuario asignado) {
        this.tareaRepository.asignarTarea(tarea, propietario, asignado);
    }

    public boolean asignarEstado(Tarea tarea) {
        return this.tareaRepository.cambiarEstado(tarea);
    }

    public boolean editarDatos(Tarea tarea) {
        return this.tareaRepository.editarDatos(tarea);
    }

}
