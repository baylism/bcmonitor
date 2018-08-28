package com.bcam.bcmonitor.storage;

import com.bcam.bcmonitor.model.MoneroTransaction;
import com.bcam.bcmonitor.model.ZCashTransaction;
import org.springframework.stereotype.Repository;

@Repository
public interface MoneroTransactionRepository extends TransactionRepository<MoneroTransaction> {
}
