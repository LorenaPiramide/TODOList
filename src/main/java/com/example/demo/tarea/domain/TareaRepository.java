package com.example.demo.tarea.domain;

import com.example.demo.usuario.domain.Usuario;

import java.util.List;

public interface TareaRepository {
    // El reset es para los test, si queremos comprobar, por ejemplo que en una tarea hay 1 usuario registrado, tenemos
    // que poner el reset() para que si pones fuera del test 3 usuarios, en el test se borre y solo quede 1
    void reset();
    Tarea crearTarea(Tarea tarea);
    List<Tarea> listarTareasUsuario(Usuario usuario);
//    void asignarTarea(Tarea tarea, Usuario propietario, Usuario asignado);
//    boolean cambiarEstado(Tarea tarea);
    boolean editarDatos(Tarea tarea);
}
