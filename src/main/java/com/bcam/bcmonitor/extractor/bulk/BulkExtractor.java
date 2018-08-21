package com.bcam.bcmonitor.extractor.bulk;

import com.bcam.bcmonitor.model.BitcoinBlock;
import reactor.core.publisher.Flux;

public interface BulkExtractor<T> {

    public Flux<T> saveBlocks(long fromHeight, long toHeight);

}
