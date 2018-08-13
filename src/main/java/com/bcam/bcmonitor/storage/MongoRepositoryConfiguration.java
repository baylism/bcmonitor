package com.bcam.bcmonitor.storage;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

import javax.validation.constraints.NotNull;

@EnableReactiveMongoRepositories
public class ReactiveMongoRepository extends AbstractReactiveMongoConfiguration {


    @Override
    public MongoClient reactiveMongoClient() {

        return MongoClients.create();
    }

    @Override
    protected String getDatabaseName() {
        return "reactive";
    }
}
