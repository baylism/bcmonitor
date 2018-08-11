package com.bcam.bcmonitor.storage;

import com.bcam.bcmonitor.model.AbstractBlock;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlockchainRepository extends ReactiveCrudRepository<AbstractBlock, String> {


}
