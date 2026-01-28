package com.example.demo.tarea.infraestructure;

import com.example.demo.context.db.MongoDBConnector;
import com.example.demo.context.prioridad.Prioridad;
import com.example.demo.tarea.domain.Tarea;
import com.example.demo.tarea.domain.TareaRepository;
import com.example.demo.usuario.domain.Usuario;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.BsonObjectId;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class TareaRepositoryMongo implements TareaRepository {

    private final String collectionName = "tareas";

    @Override
    public void reset() {
        MongoDBConnector.getDataBase().getCollection(collectionName).drop();
    }

    @Override
    public Tarea crearTarea(Tarea tarea) {
        MongoCollection<Document> collection = MongoDBConnector.getDataBase().getCollection(collectionName);

        Document document = new Document()
                .append("texto", tarea.getTexto());
//                .append("email", tarea.);
//                .append("fechaCreacion", tarea.getFechaCreacion())
//                .append("fechaFinalizacion", tarea.getFechaFinalizacion())
//                .append("estaCompletada", tarea.isEstaCompletada())
//                .append("propietario", usuario.getEmail())
//                .append("usuariosAsignados", new ArrayList<String>());

        InsertOneResult result = collection.insertOne(document);

        String idGenerado = ((BsonObjectId) result.getInsertedId()).getValue().toString();
        tarea.setId(idGenerado);

        return tarea;
    }

    @Override
    public List<Tarea> listarTareasUsuario(Usuario usuario) {
        MongoCollection<Document> collection = MongoDBConnector.getDataBase().getCollection(collectionName);
        List<Tarea> tareas = new ArrayList<>();

        for (Document doc : collection.find()) {
            String propietarioEmail = doc.getString("propietario");
            List<String> asignados = (List<String>) doc.get("usuariosAsignados");

            if (usuario.getEmail().equals(propietarioEmail) ||
                    (asignados != null && asignados.contains(usuario.getEmail()))) {

                Tarea tarea = new Tarea(
                        doc.getObjectId("_id").toString(),
                        doc.getString("texto")
//                        Prioridad.valueOf(doc.getString("prioridad")),
//                        doc.getDate("fechaCreacion"),
//                        doc.getDate("fechaFinalizacion"),
//                        doc.getBoolean("estaCompletada"),
//                        new Usuario(propietarioEmail, "")
                );

                List<Usuario> usuariosAsignados = new ArrayList<>();
                if (asignados != null) {
                    for (String email : asignados) {
                        usuariosAsignados.add(new Usuario(email, ""));
                    }
                }
//                tarea.setUsuariosAsignados(usuariosAsignados);

                tareas.add(tarea);
            }
        }

        return tareas;
    }

//    @Override
//    public void asignarTarea(Tarea tarea, Usuario propietario, Usuario asignado) {
//        MongoCollection<Document> collection = MongoDBConnector.getDataBase().getCollection(collectionName);
//
//        Document doc = collection.find(new Document("_id", new ObjectId(tarea.getId()))).first();
//        if (doc == null) return;
//
//        List<String> usuarios = (List<String>) doc.get("usuariosAsignados");
//        if (usuarios == null) usuarios = new ArrayList<>();
//
//        if (!usuarios.contains(asignado.getEmail())) {
//            usuarios.add(asignado.getEmail());
//            collection.updateOne(
//                    new Document("_id", new ObjectId(tarea.getId())),
//                    new Document("$set", new Document("usuariosAsignados", usuarios))
//            );
//        }
//    }

//    @Override
//    public boolean cambiarEstado(Tarea tarea) {
//        MongoCollection<Document> collection = MongoDBConnector.getDataBase().getCollection(collectionName);
//
//        UpdateResult result = collection.updateOne(
//                new Document("_id", new ObjectId(tarea.getId())),
//                new Document("$set", new Document("estaCompletada", tarea.isEstaCompletada()))
//        );
//
//        return result.getModifiedCount() > 0;
//    }

    @Override
    public boolean editarDatos(Tarea tarea) {
        MongoCollection<Document> collection = MongoDBConnector.getDataBase().getCollection(collectionName);

        Document nuevosDatos = new Document()
                .append("texto", tarea.getTexto());
//                .append("prioridad", tarea.getPrioridad().name())
//                .append("fechaFinalizacion", tarea.getFechaFinalizacion());

        UpdateResult result = collection.updateOne(
                new Document("_id", new ObjectId(tarea.getId())),
                new Document("$set", nuevosDatos)
        );

        return result.getModifiedCount() > 0;
    }
}
