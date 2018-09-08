package com.bcam.bcmonitor.storage;

import com.bcam.bcmonitor.api.admin.AdminController;
import com.bcam.bcmonitor.model.MoneroBlock;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

import javax.validation.constraints.NotNull;

/**
 *
 * bcmonitor-test
 * fr7J3IH40yFDD5MO
 *
 * Note: don't need to define a separate MongoTemplate bean as already defined in AbstractReactiveMongoConfiguration
 *
 * use spring boot configuration instead?
 *
 *
 */
@EnableReactiveMongoRepositories
public class MongoRepositoryConfiguration extends AbstractReactiveMongoConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(MongoRepositoryConfiguration.class);

    @Value("${spring.data.mongodb.uri}")
    String mongoUri;

    String mongoUsername = "main_admin";

    @Value("${MONGO_PW}")
    String mongoPassword;


    @Bean
    public MongoClient reactiveMongoClient() {

        // return MongoClients.create("mongodb+srv://bcmonitor-test:fr7J3IH40yFDD5MO@cluster0-eep72.mongodb.net/test?retryWrites=true");

        // String connectionURI =  "mongodb://" + mongoUsername + ":" + mongoPassword + "@mongos-router-0.mongos-router-service.default.svc.cluster.local:27017/blockchain";


        // logger.info("Building reactive mongo client with connection string" + connectionURI);
        // return MongoClients.create(connectionURI);


        return MongoClients.create(mongoUri);
    }


    @Override
    protected String getDatabaseName() {
        return "blockchain";
    }


    @Bean
    public ReactiveMongoTemplate reactiveMongoTemplate(MongoClient reactiveMongoClient) {
        return new ReactiveMongoTemplate(reactiveMongoClient, "blockchain");
    }

}
