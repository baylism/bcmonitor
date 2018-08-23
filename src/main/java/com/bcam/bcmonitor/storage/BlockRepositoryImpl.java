package com.bcam.bcmonitor.storage;

import com.bcam.bcmonitor.model.AbstractBlock;
import com.bcam.bcmonitor.model.BitcoinBlock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public class BlockRepositoryImpl<T extends AbstractBlock> implements BlockRepositoryCustom<T> {

    private final BlockRepository<T> repository;

    @Lazy   // to fix bean circular dependency
    @Autowired
    public BlockRepositoryImpl(BlockRepository<T> repository) {
        this.repository = repository;
    }


    @Override
    public Flux<T> findAllByHeightInRange(long fromHeight, long toHeight) {

        return repository.findAllByHeightBetween(fromHeight - 1, toHeight + 1);
    }
}
