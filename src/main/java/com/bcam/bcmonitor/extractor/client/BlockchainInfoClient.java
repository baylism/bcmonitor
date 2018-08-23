package com.bcam.bcmonitor.extractor.client;

import com.bcam.bcmonitor.model.BlockchainInfo;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public interface BlockchainInfoClient {

    Mono<BlockchainInfo> getBlockchainInfo();

}
