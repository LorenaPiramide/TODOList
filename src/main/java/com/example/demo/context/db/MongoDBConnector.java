package com.example.demo.context.db;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MongoDBConnector {
    private static MongoDBConnector mongoDBConnector;
    private static MongoClient mongoClient;
    private static MongoDatabase database;
    private static String url;
    private static String databasename;

    @Value("${spring.datasource.url}")
    public void setMongoClient(String url) {
        MongoDBConnector.url = url;
    }

    @Value("${spring.datasource.database}")
    public void setDatabase(String databasename) {
        MongoDBConnector.databasename = databasename;
    }

    private MongoDBConnector() {
        this.mongoClient = MongoClients.create(MongoDBConnector.url);
        this.database = mongoClient.getDatabase(MongoDBConnector.databasename);
    }

    public static MongoDatabase getDataBase() {
        if (mongoDBConnector == null) {
            mongoDBConnector = new MongoDBConnector();
        }
        return mongoDBConnector.database;
    }
}
