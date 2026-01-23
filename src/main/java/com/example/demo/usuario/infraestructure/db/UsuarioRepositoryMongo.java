package com.example.demo.usuario.infraestructure.db;

import com.example.demo.context.db.MongoDBConnector;
import com.example.demo.usuario.domain.Usuario;
import com.example.demo.usuario.domain.UsuarioRepository;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.InsertOneResult;
import org.bson.Document;

public class UsuarioRepositoryMongo implements UsuarioRepository {

    private final String collectionName = "usuarios";

    @Override
    public void reset() {
        MongoDBConnector.getDataBase().getCollection(collectionName).drop();
    }

    @Override
    public boolean registrarUsuario(Usuario usuario) {
        Document document = new Document();
        document.append("email", usuario.getEmail());
        document.append("password", usuario.getPassword());
        document.append("creadas", usuario.getCreadas());
        document.append("asignadas", usuario.getAsignadas());
        InsertOneResult result = MongoDBConnector.getDataBase().getCollection(collectionName).insertOne(document);
        return result.wasAcknowledged(); //Esto devuelve un true o un false si Mongo a recibido y confirmado la petición o no
    }

    @Override
    public Usuario loginUsuario(Usuario usuario) {
        MongoCollection<Document> collection = MongoDBConnector.getDataBase().getCollection(collectionName);
        Document query = new Document().append("email", usuario.getEmail()).append("password", usuario.getPassword());
        Document document = collection.find(query).first();
        if (document == null) {
            return null;
        }
        return new Usuario(document.getString("email"), document.getString("password"));
    }

    // todo, creo que no hay que listar los usuarios
//    @Override
//    public List<Usuario> listarUsuarios() {
//        MongoCollection<Document> collection = MongoDBConnector.getDataBase().getCollection(collectionName);
//        List<Usuario> usuarios = new ArrayList<>();
//        FindIterable<Document> iterable = collection.find();
//
//        for (Document document : iterable) {
//            Usuario usuario = new Usuario(document.getString("email"), document.getString("password"));
//            usuarios.add(usuario);
//        }
//        return usuarios;
//    }
}
