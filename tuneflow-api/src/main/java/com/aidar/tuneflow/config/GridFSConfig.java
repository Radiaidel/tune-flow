package com.aidar.tuneflow.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.data.mongodb.core.MongoClientFactoryBean;

@Configuration
public class GridFSConfig {

    private final MongoDatabaseFactory dbFactory;
    private final MongoConverter converter;
    private final MongoClient mongoClient;

    @Value("${spring.data.mongodb.database}")
    private String databaseName;  // Injecte le nom de la base de données depuis application.yml

    public GridFSConfig(MongoDatabaseFactory dbFactory, MongoConverter converter, MongoClient mongoClient) {
        this.dbFactory = dbFactory;
        this.converter = converter;
        this.mongoClient = mongoClient;
    }

    @Bean
    public GridFSBucket gridFSBucket() {
        return GridFSBuckets.create(mongoClient.getDatabase(databaseName));  // Utilisation du nom de base de données injecté
    }

    @Bean
    public GridFsTemplate gridFsTemplate() {
        return new GridFsTemplate(dbFactory, converter);
    }
}
