package com.bcam.bcmonitor.storage;

import com.bcam.bcmonitor.model.BitcoinBlock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public class BlockRepositoryImpl implements BlockRepositoryCustom {

    private final BlockRepository repository;

    @Lazy   // to fix bean circular dependency
    @Autowired
    public BlockRepositoryImpl(BlockRepository repository) {
        this.repository = repository;
    }


    @Override
    public Flux<BitcoinBlock> findAllByHeightInRange(long fromHeight, long toHeight) {

        return repository.findAllByHeightBetween(fromHeight - 1, toHeight + 1);
    }
}
