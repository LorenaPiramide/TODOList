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
    private String url;
    private String databasename;


    private MongoDBConnector(
            @Value("${spring.datasource.url}") String url,
            @Value("${spring.datasource.database}") String databasename
    ) {
        this.url = url;
        this.databasename = databasename;
        this.mongoClient = MongoClients.create(url);
        this.database = mongoClient.getDatabase(databasename);
    }

    public static MongoDatabase getDataBase() {
        if (mongoDBConnector == null) {
            mongoDBConnector = new MongoDBConnector(mongoDBConnector.url, mongoDBConnector.databasename);
        }
        return mongoDBConnector.database;
    }
}
