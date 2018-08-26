package com.bcam.bcmonitor.storage;

import com.bcam.bcmonitor.model.BitcoinTransaction;
import org.springframework.stereotype.Repository;

@Repository
public interface BitcoinTransactionRepository extends TransactionRepository<BitcoinTransaction> {
}
