package com.bcam.bcmonitor.storage;

import com.bcam.bcmonitor.model.AbstractBlock;
import com.bcam.bcmonitor.model.BitcoinBlock;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


/**
 * from talk:
 * tail a mongodb collection with @Tailable
 *
 * use a redis for caching
 *
 * for send application/stream json Flux<block>
 *
 *
 */
@Repository
public interface BlockRepository extends ReactiveMongoRepository<BitcoinBlock, String>, BlockRepositoryCustom{

    Mono<BitcoinBlock> findByHeight(long height);

    Flux<BitcoinBlock> findAllByHeightBetween(long fromHeight, long toHeight);

}
