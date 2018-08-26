package com.bcam.bcmonitor.storage;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
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
@EnableReactiveMongoRepositories()
public class MongoRepositoryConfiguration extends AbstractReactiveMongoConfiguration {

    @Value("${spring.data.mongodb.uri}")
    String mongoUri;

    @Bean
    public MongoClient reactiveMongoClient() {

        // return MongoClients.create("mongodb+srv://bcmonitor-test:fr7J3IH40yFDD5MO@cluster0-eep72.mongodb.net/test?retryWrites=true");
        return MongoClients.create(mongoUri);
    }


    @Override
    protected String getDatabaseName() {
        return "test";
    }


    @Bean
    public ReactiveMongoTemplate reactiveMongoTemplate(MongoClient reactiveMongoClient) {
        return new ReactiveMongoTemplate(reactiveMongoClient, "test");
    }

}
