package com.example.demo.context.db;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongoDBConnector {
    private static MongoDBConnector mongoDBConnector;
    private final MongoClient mongoClient;
    private final MongoDatabase database;

    private MongoDBConnector() {
        this.mongoClient = MongoClients.create("mongodb+srv://dwes:IHgiOEisnKh6QKF1@clusterdwes.bjtt9c9.mongodb.net/?appName=ClusterDWES");
        this.database = mongoClient.getDatabase("entrenamientos");
    }

    public static MongoDatabase getDataBase() {
        if (mongoDBConnector == null) {
            mongoDBConnector = new MongoDBConnector();
        }
        return mongoDBConnector.database;
    }
}
