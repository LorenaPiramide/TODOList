package com.example.demo.usuario.infraestructure.db;

import com.example.demo.context.db.MongoDBConnector;
import com.example.demo.usuario.domain.Usuario;
import com.example.demo.usuario.domain.UsuarioRepository;
import com.google.common.hash.Hashing;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.InsertOneResult;
import org.bson.Document;

import java.nio.charset.StandardCharsets;

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
        String password = Hashing.sha256().hashString(usuario.getPassword(), StandardCharsets.UTF_8).toString();
        Document query = new Document().append("email", usuario.getEmail()).append("password", password);
        Document document = collection.find(query).first();
        if (document == null) {
            return null;
        }
        return new Usuario(document.getString("email"), document.getString("password"));
    }
}
