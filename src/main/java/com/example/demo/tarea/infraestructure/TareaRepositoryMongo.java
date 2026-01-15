package com.example.demo.tarea.infraestructure;

import com.example.demo.context.db.MongoDBConnector;
import com.example.demo.tarea.domain.Tarea;
import com.example.demo.tarea.domain.TareaRepository;
import com.example.demo.usuario.domain.Usuario;
import com.mongodb.client.result.InsertManyResult;
import org.bson.Document;

import java.util.List;

public class TareaRepositoryMongo implements TareaRepository {

    private final String collectionName = "tareas";

    @Override
    public void reset() {
        MongoDBConnector.getDataBase().getCollection(collectionName).drop();
    }

    @Override
    public void crearTarea(Tarea tarea, Usuario usuario) {
        Document document = new Document();
        document.append("texto", tarea.getTexto());
        document.append("prioridad", tarea.getPrioridad());
        document.append("fechaCreacion", tarea.getFechaCreacion());
        document.append("fechaFinalizacion", tarea.getFechaFinalizacion());
        document.append("estaCompletada", tarea.isEstaCompletada());
        document.append("propietario", tarea.getPropietario());
        document.append("usuariosAsignados", tarea.getUsuariosAsignados());
        InsertManyResult result = MongoDBConnector.getDataBase().getCollection(collectionName).insertMany(document);
    }

    @Override
    public List<Tarea> listarTareasUsuario(Usuario usuario) {
        return List.of();
    }

    @Override
    public void asignarTarea(Tarea tarea, Usuario propietario, Usuario asignado) {

    }

    @Override
    public boolean cambiarEstado(Tarea tarea) {
        return false;
    }

    @Override
    public boolean editarDatos(Tarea tarea) {
        return false;
    }
}
