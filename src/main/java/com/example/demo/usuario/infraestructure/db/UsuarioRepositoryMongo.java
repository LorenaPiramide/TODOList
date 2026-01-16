package com.example.demo.usuario.infraestructure.db;

import com.example.demo.context.db.MongoDBConnector;
import com.example.demo.usuario.domain.Usuario;
import com.example.demo.usuario.domain.UsuarioRepository;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.InsertOneResult;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class UsuarioRepositoryMongo implements UsuarioRepository {

    private final String collectionName = "usuarios";

    @Override
    public Usuario registrarUsuario(Usuario usuario) {
        Document document = new Document();
        document.append("email", usuario.getEmail());
        document.append("password", usuario.getPassword());
        document.append("creadas", usuario.getCreadas());
        document.append("asignadas", usuario.getAsignadas());
        InsertOneResult result = MongoDBConnector.getDataBase().getCollection(collectionName).insertOne(document);
        return new Usuario(usuario.getEmail(), usuario.getPassword());
    }

    // No hay que mostrar la contraseña, es para probar
    @Override
    public List<Usuario> listarUsuarios() {
        MongoCollection<Document> collection = MongoDBConnector.getDataBase().getCollection(collectionName);
        List<Usuario> usuarios = new ArrayList<>();
        FindIterable<Document> iterable = collection.find();

        for (Document document : iterable) {
            Usuario usuario = new Usuario(document.getString("email"), document.getString("password"));
            usuarios.add(usuario);
        }
        return usuarios;
    }
}
