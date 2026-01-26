package com.example.demo;

import com.example.demo.context.prioridad.Prioridad;
import com.example.demo.tarea.application.TareaUseCases;
import com.example.demo.tarea.domain.Tarea;
import com.example.demo.tarea.infraestructure.TareaRepositoryMongo;
import com.example.demo.usuario.domain.Usuario;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@SpringBootTest
class TareaUseCasesTest {

    private final TareaUseCases tareaUseCases;

    public TareaUseCasesTest() {
        this.tareaUseCases = new TareaUseCases(new TareaRepositoryMongo());
    }

    @Test
    void reset() {
        this.tareaUseCases.reset();
    }

    @Test
    void crearTarea() {

        tareaUseCases.reset();

        Usuario usuario = new Usuario("test@test", "pass");
        Tarea tarea = new Tarea(null, "Prueba", Prioridad.MEDIA, new Date(), null, false, usuario);

        tareaUseCases.crearTarea(tarea, usuario);

        List<Tarea> tareas = tareaUseCases.listarTareasUsuario(usuario);
        assertEquals(1, tareas.size(), "No existe la tarea.");
        assertEquals("Prueba", tareas.getFirst().getTexto(), "El texto no coincide");
    }


    @Test
    void listarTareasUsuario() {

        tareaUseCases.reset();

        Usuario usuario1 = new Usuario("user1@test", "pass1");
        Usuario usuario2 = new Usuario("user2@test", "pass2");

        Tarea tarea1 = new Tarea(null, "Tarea 1", Prioridad.ALTA, new Date(), null, false, usuario1);
        Tarea tarea2 = new Tarea(null, "Tarea 2", Prioridad.BAJA, new Date(), null, false, usuario2);

        tareaUseCases.crearTarea(tarea1, usuario1);
        tareaUseCases.crearTarea(tarea2, usuario2);

        tareaUseCases.asignarTarea(tarea1, usuario1, usuario2);

        List<Tarea> tareasUsuario1 = tareaUseCases.listarTareasUsuario(usuario1);

        assertEquals(1, tareasUsuario1.size(), "Usuario1 tiene 1 tarea.");

        boolean tarea1Encontrada = false;
        boolean tarea2Encontrada = false;

        for (Tarea tarea : tareasUsuario1) {
            if (tarea.getTexto().equals("Tarea 1")) {
                tarea1Encontrada = true;
            }
            if (tarea.getTexto().equals("Tarea 2")) {
                tarea2Encontrada = true;
            }
        }

        assertTrue(tarea1Encontrada, "La tarea 1 debe existir.");
        assertFalse(tarea2Encontrada, "La tarea 2 no pertenece a usuario1.");
    }


    @Test
    void asignarTarea() {
        Usuario propietario = new Usuario("usuario@test", "pass1");
        Usuario asignado = new Usuario("prueba@test", "pass2");

        Tarea tarea = new Tarea(null, "Tarea", Prioridad.MEDIA, new Date(), null, false, propietario);
        tareaUseCases.crearTarea(tarea, propietario);

        tareaUseCases.asignarTarea(tarea, propietario, asignado);

        List<Tarea> tareas = tareaUseCases.listarTareasUsuario(asignado);
        assertEquals(1, tareas.size(), "El usuario asignado debe tener la tarea");
        assertEquals("Tarea", tareas.getFirst().getTexto());
    }

    @Test
    void asignarEstado() {
        Usuario usuario = new Usuario("test@test", "pass");
        Tarea tarea = new Tarea(null, "Tarea", Prioridad.BAJA, new Date(), null, false, usuario);
        tareaUseCases.crearTarea(tarea, usuario);

        tarea.setEstaCompletada(true);
        boolean estaModificado = tareaUseCases.asignarEstado(tarea);

        assertTrue(estaModificado, "La tarea debe haberse modificado.");

        List<Tarea> tareas = tareaUseCases.listarTareasUsuario(usuario);
        assertTrue(tareas.getFirst().isEstaCompletada(), "La tarea debe estar completada.");
    }

    @Test
    void editarDatos() {
        Usuario usuario = new Usuario("user@test", "pass");
        Tarea tarea = new Tarea(null, "Texto original", Prioridad.BAJA, new Date(), null, false, usuario);
        tareaUseCases.crearTarea(tarea, usuario);

        tarea.setTexto("Texto editado");
        tarea.setPrioridad(Prioridad.ALTA);

        boolean estaModificado = tareaUseCases.editarDatos(tarea);

        assertTrue(estaModificado, "La tarea debe haberse modificado.");

        List<Tarea> tareas = tareaUseCases.listarTareasUsuario(usuario);
        assertEquals("Texto editado", tareas.getFirst().getTexto());
        assertEquals(Prioridad.ALTA, tareas.getFirst().getPrioridad());
    }
}