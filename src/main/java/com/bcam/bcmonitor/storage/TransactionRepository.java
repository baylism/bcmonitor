package com.bcam.bcmonitor.storage;

import com.bcam.bcmonitor.model.AbstractTransaction;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;


public interface TransactionRepository<T extends AbstractTransaction> extends ReactiveMongoRepository<T, String> {

}
