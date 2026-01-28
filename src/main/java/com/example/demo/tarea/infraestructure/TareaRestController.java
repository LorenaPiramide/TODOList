package com.example.demo.tarea.infraestructure;

import com.example.demo.tarea.application.TareaUseCases;
import com.example.demo.tarea.domain.Tarea;
import com.example.demo.usuario.application.UsuarioUseCases;
import com.example.demo.usuario.domain.Usuario;
import com.example.demo.usuario.infraestructure.db.UsuarioRepositoryMongo;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tareas")
public class TareaRestController {
    private TareaUseCases tareaUseCases;

    public TareaRestController() {
        this.tareaUseCases = new TareaUseCases(new TareaRepositoryMongo());
    }

    @GetMapping("/")
    public List<Tarea> verTareas(@PathVariable String email) {
        Usuario usuario = new Usuario(email, "");
        return tareaUseCases.listarTareasUsuario(usuario);
    }

    @PostMapping("/{email}")
    public void crearTarea(@RequestBody Tarea tarea, @PathVariable String email) {
//        Usuario usuario = new Usuario(email, "");
        tareaUseCases.crearTarea(tarea, email);
    }

//    @PutMapping("/{id}/asignar/{emailAsignado}")
//    public void asignarTarea(@PathVariable String id, @PathVariable String emailAsignado, @RequestBody String emailPropietario) {
//        Tarea tarea = new Tarea();
//        tarea.setId(id);
//        Usuario propietario = new Usuario(emailPropietario, "");
//        Usuario asignado = new Usuario(emailAsignado, "");
//        tareaUseCases.asignarTarea(tarea, propietario, asignado);
//    }

//    @PutMapping("/{id}/estado")
//    public void cambiarEstado(@PathVariable String id, @RequestBody Boolean estado) {
//        Tarea tarea = new Tarea();
//        tarea.setId(id);
//        tarea.setEstaCompletada(estado);
//        tareaUseCases.asignarEstado(tarea);
//    }

    @PutMapping("/{id}/datos")
    public void modificarTarea(@PathVariable String id, @RequestBody Tarea tarea) {
        tarea.setId(id);
        tareaUseCases.editarDatos(tarea);
    }
}
