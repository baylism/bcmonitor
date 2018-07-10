package com.bcam.bcmonitor.api;


import com.bcam.bcmonitor.extractor.client.ReactiveDashClient;
import com.bcam.bcmonitor.model.BitcoinBlock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.io.IOException;

@RestController
@RequestMapping("/api/dash")
public class DashController {

    private ReactiveDashClient client;

    @Autowired
    public DashController(ReactiveDashClient client) {
        this.client = client;
    }

    @GetMapping("/blockchaininfo")
    Mono<String> getInfo() {
        // return client.getInfo("00000ffd590b1485b3caadc19b22e6379c733355108f107a430458cdf3407ab6");
        return client.getInfo();
    }

    @GetMapping("/block/{hash}")
    Mono<BitcoinBlock> getBlock(@PathVariable String hash) {
        return client.getBlock(hash);
    }

    @GetMapping("/blockstring/{hash}")
    Mono<String> getBlockString(@PathVariable String hash) {
        return client.getBlockString(hash);
    }

    @GetMapping("/better/{hash}")
    Mono<BitcoinBlock> gb(@PathVariable String hash) throws IOException {
        return client.getBetter(hash);
    }



}
