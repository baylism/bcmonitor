package com.bcam.bcmonitor.storage;

import com.bcam.bcmonitor.model.MoneroBlock;
import com.bcam.bcmonitor.model.ZCashBlock;
import org.springframework.stereotype.Repository;

@Repository
public interface MoneroBlockRepository extends BlockRepository<MoneroBlock> {
}
