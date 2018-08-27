package com.bcam.bcmonitor.extractor.bulk;

import com.bcam.bcmonitor.model.AbstractBlock;
import com.bcam.bcmonitor.model.AbstractTransaction;
import com.bcam.bcmonitor.model.BitcoinBlock;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;

public interface BulkExtractor<B extends AbstractBlock, T extends AbstractTransaction> {

    Flux<B> saveBlocks(long fromHeight, long toHeight);

    Flux<T> saveTransactions(Flux<B> blocks);

    Flux<T> saveTransactions(B block);

    Disposable saveBlocksAndTransactions(long fromHeight, long toHeight);

    Flux<T> saveBlocksAndTransactionsForward(long fromHeight, long toHeight);
}
