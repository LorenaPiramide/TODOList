package com.example.demo.tarea.infraestructure;

import com.example.demo.context.db.MongoDBConnector;
import com.example.demo.context.prioridad.Prioridad;
import com.example.demo.tarea.domain.Tarea;
import com.example.demo.tarea.domain.TareaRepository;
import com.example.demo.usuario.domain.Usuario;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.BsonObjectId;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TareaRepositoryMongo implements TareaRepository {

    private final String collectionName = "tareas";
    private final DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

    @Override
    public void reset() {
        MongoDBConnector.getDataBase().getCollection(collectionName).drop();
    }

    @Override
    public Tarea crearTarea(Tarea tarea, Usuario usuario) {
        Document document = new Document();
        document.append("texto", tarea.getTexto());
        document.append("prioridad", tarea.getPrioridad());
        document.append("fechaCreacion", tarea.getFechaCreacion());
        document.append("fechaFinalizacion", tarea.getFechaFinalizacion());
        document.append("estaCompletada", tarea.isEstaCompletada());
        document.append("propietario", tarea.getPropietario());
        document.append("usuariosAsignados", tarea.getUsuariosAsignados());
        InsertOneResult result = MongoDBConnector.getDataBase().getCollection(collectionName).insertOne(document);
        return new Tarea(
                ((BsonObjectId)(result.getInsertedId())).getValue().toString(), 
                tarea.getTexto(), tarea.getPrioridad(), tarea.getFechaCreacion(), tarea.getFechaFinalizacion(),
                tarea.isEstaCompletada(), tarea.getPropietario()
        );
    }

    @Override
    public List<Tarea> listarTareasUsuario(Usuario usuario) {
        MongoCollection<Document> collection = MongoDBConnector.getDataBase().getCollection(collectionName);
        List<Tarea> tareas = new ArrayList<>();

        collection.find(
                Filters.or(
                        Filters.eq("propietario", usuario.getEmail()),
                        Filters.in("usuariosAsignados", usuario.getEmail())
                )
        ).forEach(doc -> {
            Prioridad prioridad = Prioridad.valueOf(doc.getString("prioridad"));
            LocalDateTime fechaCreacion = LocalDateTime.parse(doc.getString("fechaCreacion"), formatter);
            LocalDateTime fechaFinalizacion = null;
            String fechaFinalizacionStr = doc.getString("fechaFinalizacion");
            if (fechaFinalizacionStr != null) {
                fechaFinalizacion = LocalDateTime.parse(fechaFinalizacionStr, formatter);
            }
            Usuario propietario = new Usuario(doc.getString("propietario"), "");

            Tarea tarea = new Tarea(
                    doc.getObjectId("_id").toString(),
                    doc.getString("texto"),
                    prioridad,
                    fechaCreacion,
                    fechaFinalizacion,
                    doc.getBoolean("estaCompletada"),
                    propietario
            );

            List<Usuario> asignados = new ArrayList<>();
            List<String> emails = (List<String>) doc.get("usuariosAsignados");
            if (emails != null) {
                for (String email : emails) {
                    asignados.add(new Usuario(email, ""));
                }
            }
            tarea.setUsuariosAsignados(asignados);
            tareas.add(tarea);
        });

        return tareas;
    }

    @Override
    public void asignarTarea(Tarea tarea, Usuario propietario, Usuario asignado) {
        MongoCollection<Document> collection = MongoDBConnector.getDataBase().getCollection(collectionName);
        Bson filter = Filters.eq("_id", new ObjectId(tarea.getId()));
        Bson update = Updates.addToSet("usuariosAsignados", asignado.getEmail());
        collection.updateOne(filter, update);
    }

    @Override
    public boolean cambiarEstado(Tarea tarea) {
        MongoCollection<Document> collection = MongoDBConnector.getDataBase().getCollection(collectionName);
        Bson filter = Filters.eq("_id", new ObjectId(tarea.getId()));
        Bson update = Updates.set("estaCompletada", tarea.isEstaCompletada());
        UpdateResult result = collection.updateOne(filter, update);
        return result.getModifiedCount() > 0;
    }

    @Override
    public boolean editarDatos(Tarea tarea) {
        MongoCollection<Document> collection = MongoDBConnector.getDataBase().getCollection(collectionName);

        String fechaFinalizacionStr = null;
        if (tarea.getFechaFinalizacion() != null) {
            fechaFinalizacionStr = tarea.getFechaFinalizacion().format(formatter);
        }

        Bson filter = Filters.eq("_id", new ObjectId(tarea.getId()));
        Bson update = Updates.combine(
                Updates.set("texto", tarea.getTexto()),
                Updates.set("prioridad", tarea.getPrioridad().name()),
                Updates.set("fechaFinalizacion", fechaFinalizacionStr)
        );

        UpdateResult result = collection.updateOne(filter, update);
        return result.getModifiedCount() > 0;
    }

}
