package com.bcam.bcmonitor.storage;

import com.bcam.bcmonitor.model.DashTransaction;
import com.bcam.bcmonitor.model.ZCashTransaction;
import org.springframework.stereotype.Repository;

@Repository
public interface DashTransactionRepository extends TransactionRepository<DashTransaction> {
}
