package com.bcam.bcmonitor.storage;

import com.bcam.bcmonitor.model.BitcoinTransaction;
import com.bcam.bcmonitor.model.ZCashTransaction;
import org.springframework.stereotype.Repository;

@Repository
public interface ZCashTransactionRepository extends TransactionRepository<ZCashTransaction> {
}
