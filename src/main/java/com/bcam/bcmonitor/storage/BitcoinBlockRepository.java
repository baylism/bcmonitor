package com.bcam.bcmonitor.storage;

import com.bcam.bcmonitor.model.BitcoinBlock;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface BitcoinBlockRepository extends BlockRepository<BitcoinBlock> {

}
