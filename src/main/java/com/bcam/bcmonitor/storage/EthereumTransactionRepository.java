package com.bcam.bcmonitor.storage;

import com.bcam.bcmonitor.model.DashTransaction;
import com.bcam.bcmonitor.model.EthereumTransaction;
import org.springframework.stereotype.Repository;

@Repository
public interface EthereumTransactionRepository extends TransactionRepository<EthereumTransaction> {
}
