package com.bcam.bcmonitor.extractor.client;

import com.bcam.bcmonitor.model.BlockchainInfo;
import reactor.core.publisher.Mono;

public interface BlockchainInfoClient {

    Mono<BlockchainInfo> getBlockchainInfo();

}
