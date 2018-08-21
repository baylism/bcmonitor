package com.bcam.bcmonitor.extractor.client;

import com.bcam.bcmonitor.model.AbstractBlock;
import com.bcam.bcmonitor.model.AbstractTransaction;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public interface ReactiveClient<B extends AbstractBlock, T extends AbstractTransaction> {

    Mono<B> getBlock(String hash);

    Mono<T> getTransaction(String hash);

    Mono<String> getBlockHash(long height);

}
