package com.bcam.bcmonitor.storage;

import com.bcam.bcmonitor.model.BitcoinBlock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MongoService<T> {

    final ReactiveMongoTemplate template;

    @Autowired
    public MongoService(ReactiveMongoTemplate template) {
        this.template = template;
    }

    // public Flux<T> insertBlock(Mono<T> block) {
    //     return template.save(T);
    // }

    // public Mono<BitcoinBlock> findById(String id) {
    //     return template.findById(id, Account.class);
    // }
    //
    // public Flux<Account> findAll() {
    //     return template.findAll(Account.class);
    // }
    // public Mono<Account> save(Mono<Account> account) {
    //     return template.save(account);
    // }
}
