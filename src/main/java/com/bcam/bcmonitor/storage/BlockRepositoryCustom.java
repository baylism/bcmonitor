package com.bcam.bcmonitor.storage;

import com.bcam.bcmonitor.model.BitcoinBlock;
import reactor.core.publisher.Flux;

public interface BlockRepositoryCustom<T> {

    Flux<T> findAllByHeightInRange(long fromHeight, long toHeight);


}
