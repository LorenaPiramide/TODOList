package com.example.demo.tarea.infraestructure;

import com.example.demo.tarea.application.TareaUseCases;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tareas")
public class TareaRestController {
    private TareaUseCases tareaUseCases;

    public TareaRestController() {
        this.tareaUseCases = new TareaUseCases(new TareaRepositoryMongo());
    }
}
