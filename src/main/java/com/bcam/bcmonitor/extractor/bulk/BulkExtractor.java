package com.bcam.bcmonitor.extractor.bulk;

import com.bcam.bcmonitor.model.AbstractBlock;
import com.bcam.bcmonitor.model.AbstractTransaction;
import com.bcam.bcmonitor.model.BitcoinBlock;
import reactor.core.publisher.Flux;

public interface BulkExtractor<B, T> {

    public Flux<B> saveBlocks(long fromHeight, long toHeight);

}
