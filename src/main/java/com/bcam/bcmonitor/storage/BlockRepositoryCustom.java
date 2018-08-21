package com.bcam.bcmonitor.storage;

import reactor.core.publisher.Flux;

public interface BlockRepositoryCustom<T> {

    Flux<T> findAllByHeightInRange(long fromHeight, long toHeight);



}
