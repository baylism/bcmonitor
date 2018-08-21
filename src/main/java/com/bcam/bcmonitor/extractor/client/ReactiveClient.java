package com.bcam.bcmonitor.extractor.client;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public interface ReactiveClient<B, T> {

    Mono<B> getBlock(String hash);

    Mono<T> getTransaction(String hash);

}
