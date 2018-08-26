package com.bcam.bcmonitor.storage;

import com.bcam.bcmonitor.model.BitcoinBlock;
import com.bcam.bcmonitor.model.Blockchain;
import org.springframework.stereotype.Repository;

@Repository
public interface BitcoinBlockRepository extends BlockRepository<BitcoinBlock> {

}
