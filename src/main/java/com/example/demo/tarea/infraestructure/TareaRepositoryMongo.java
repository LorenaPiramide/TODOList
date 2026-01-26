package com.example.demo.tarea.infraestructure;

import com.example.demo.context.db.MongoDBConnector;
import com.example.demo.context.prioridad.Prioridad;
import com.example.demo.tarea.domain.Tarea;
import com.example.demo.tarea.domain.TareaRepository;
import com.example.demo.usuario.domain.Usuario;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
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
        MongoCollection<Document> collection = MongoDBConnector.getDataBase().getCollection(collectionName);

        Document document = new Document();
        document.append("texto", tarea.getTexto());
        document.append("prioridad", tarea.getPrioridad().name());
        document.append("fechaCreacion", tarea.getFechaCreacion().toString());
        document.append("fechaFinalizacion", tarea.getFechaFinalizacion() != null ? tarea.getFechaFinalizacion().toString() : null);
        document.append("estaCompletada", tarea.isEstaCompletada());
        document.append("propietario", usuario.getEmail());
        document.append("usuariosAsignados", new ArrayList<String>());

        InsertOneResult result = collection.insertOne(document);

        String idGenerado = ((BsonObjectId) result.getInsertedId()).getValue().toString();
        tarea.setId(idGenerado);

        return tarea;
    }

    @Override
    public List<Tarea> listarTareasUsuario(Usuario usuario) {
        MongoCollection<Document> collection = MongoDBConnector.getDataBase().getCollection(collectionName);
        List<Tarea> tareas = new ArrayList<>();

        MongoCursor<Document> cursor = collection.find(
                Filters.or(
                        Filters.eq("propietario", usuario.getEmail()),
                        Filters.eq("usuariosAsignados", usuario.getEmail())
                )
        ).iterator();

        while (cursor.hasNext()) {
            Document doc = cursor.next();

            Prioridad prioridad = Prioridad.valueOf(doc.getString("prioridad"));

            LocalDateTime fechaCreacion = LocalDateTime.parse(doc.getString("fechaCreacion"), formatter);

            LocalDateTime fechaFinalizacion = null;
            String fechaFinalizacionStr = doc.getString("fechaFinalizacion");
            if (fechaFinalizacionStr != null) {
                fechaFinalizacion = LocalDateTime.parse(fechaFinalizacionStr, formatter);
            }

            Usuario propietario = new Usuario(doc.getString("propietario"), "");

            List<Usuario> asignados = new ArrayList<>();
            List<String> emails = (List<String>) doc.get("usuariosAsignados");
            if (emails != null) {
                for (String email : emails) {
                    asignados.add(new Usuario(email, ""));
                }
            }

            Tarea tarea = new Tarea(
                    doc.getObjectId("_id").toString(),
                    doc.getString("texto"),
                    prioridad,
                    fechaCreacion,
                    fechaFinalizacion,
                    doc.getBoolean("estaCompletada"),
                    propietario
            );
            tarea.setUsuariosAsignados(asignados);
            tareas.add(tarea);
        }

        return tareas;
    }

    @Override
    public void asignarTarea(Tarea tarea, Usuario propietario, Usuario asignado) {
        MongoCollection<Document> collection = MongoDBConnector.getDataBase().getCollection(collectionName);

        Document doc = collection.find(new Document("_id", new ObjectId(tarea.getId()))).first();
        if (doc == null) return;

        List<String> usuarios = (List<String>) doc.get("usuariosAsignados");
        if (usuarios == null) {
            usuarios = new ArrayList<>();
        }

        if (!usuarios.contains(asignado.getEmail())) {
            usuarios.add(asignado.getEmail());

            collection.updateOne(
                    new Document("_id", new ObjectId(tarea.getId())),
                    new Document("$set", new Document("usuariosAsignados", usuarios))
            );
        }
    }


    @Override
    public boolean cambiarEstado(Tarea tarea) {
        MongoCollection<Document> collection = MongoDBConnector.getDataBase().getCollection(collectionName);

        Document nuevosDatos = new Document("estaCompletada", tarea.isEstaCompletada());

        UpdateResult result = collection.updateOne(
                new Document("_id", new ObjectId(tarea.getId())),
                new Document("$set", nuevosDatos)
        );

        return result.getModifiedCount() > 0;
    }


    @Override
    public boolean editarDatos(Tarea tarea) {
        MongoCollection<Document> collection = MongoDBConnector.getDataBase().getCollection(collectionName);

        String fechaFinalizacion = null;
        if (tarea.getFechaFinalizacion() != null) {
            fechaFinalizacion = tarea.getFechaFinalizacion().format(formatter);
        }

        Document nuevosDatos = new Document()
                .append("texto", tarea.getTexto())
                .append("prioridad", tarea.getPrioridad().name())
                .append("fechaFinalizacion", fechaFinalizacion);

        UpdateResult result = collection.updateOne(
                new Document("_id", new ObjectId(tarea.getId())),
                new Document("$set", nuevosDatos)
        );

        return result.getModifiedCount() > 0;
    }

}
