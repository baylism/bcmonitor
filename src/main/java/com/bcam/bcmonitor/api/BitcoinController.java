package com.bcam.bcmonitor.api;


import com.bcam.bcmonitor.extractor.client.ReactiveBitcoinClient;
import com.bcam.bcmonitor.extractor.client.ReactiveDashClient;
import com.bcam.bcmonitor.model.BitcoinBlock;
import com.bcam.bcmonitor.model.BitcoinTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/bitcoin")
public class BitcoinController {

    private ReactiveBitcoinClient client;

    @Autowired
    public BitcoinController(ReactiveDashClient client) {
        this.client = client;
    }

    @GetMapping("/blockchaininfo")
    Mono<String> getInfo() {
        return client.getBlockchainInfo();
    }

    @GetMapping("/block/{hash}")
    Mono<BitcoinBlock> getBlock(@PathVariable String hash) {
        return client.getBlock(hash);
    }

    @GetMapping("/transaction{hash}")
    Mono<BitcoinTransaction> getTransaction(@PathVariable String hash) {
        return client.getTransaction(hash);
    }

}
