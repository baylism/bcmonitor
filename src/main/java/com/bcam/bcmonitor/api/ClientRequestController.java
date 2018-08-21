package com.bcam.bcmonitor.api;

import com.bcam.bcmonitor.extractor.client.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class ClientRequestController {

    @Qualifier("ReactiveZCashClient")
    private ReactiveZCashClient zCashClient;
    private ReactiveDashClient dashClient;
    private ReactiveBitcoinClient bitcoinClient;

    @Autowired
    public ClientRequestController(
            @Qualifier("ReactiveZCashClient") ReactiveZCashClient zCashClient,
            @Qualifier("ReactiveDashClient") ReactiveDashClient dashClient,
            @Qualifier("ReactiveBitcoinClient") ReactiveBitcoinClient bitcoinClient
    ) {
        this.zCashClient = zCashClient;
        this.dashClient = dashClient;
        this.bitcoinClient = bitcoinClient;
    }

    // ============ client provided requests ============
    @GetMapping("/{blockchain}/raw/{jsonQuery}")
    Mono<String> getRawResponse(@PathVariable String blockchain, @PathVariable String jsonQuery) {

        return getClient(blockchain).getRawResponse(jsonQuery);
    }

    @GetMapping("/{blockchain}/method/{methodName}")
    Mono<String> getCustomResponse(@PathVariable String blockchain, @PathVariable String methodName) {

        return getClient(blockchain).getCustomResponse(methodName);
    }

    // match requests where param contains a letter (e.g. a hash)
    @GetMapping("/{blockchain}/method/{methodName}/{param:.*[a-z]+.*}")
    Mono<String> getCustomResponse(@PathVariable String blockchain, @PathVariable String methodName, @PathVariable() String param) {

        return getClient(blockchain).getCustomResponse(methodName, param);
    }

    // match requests where param contains only numbers (e.g. a block height)
    @GetMapping("/{blockchain}/method/{methodName}/{param:[0-9]+}")
    Mono<String> getCustomResponse(@PathVariable String blockchain, @PathVariable String methodName, @PathVariable() int param) {

        return getClient(blockchain).getCustomResponse(methodName, param);
    }

    // only support double parameter request where the first is a string
    @GetMapping("/{blockchain}/method/{methodName}/{param}/{param2:.*[a-z]+.*}")
    Mono<String> getCustomResponse(@PathVariable String blockchain, @PathVariable String methodName, @PathVariable() String param, @PathVariable() String param2) {

        return getClient(blockchain).getCustomResponse(methodName, param, param2);
    }

    @GetMapping("/{blockchain}/method/{methodName}/{param}/{param2:[0-9]+}")
    Mono<String> getCustomResponse(@PathVariable String blockchain, @PathVariable String methodName, @PathVariable() String param, @PathVariable() int param2) {

        return getClient(blockchain).getCustomResponse(methodName, param, param2);
    }

    private ReactiveClientImpl getClient(String blockchain) {

        switch (blockchain) {
            case "dash" : return dashClient;
            case "zcash" : return zCashClient;
            case "bitcoin" : return bitcoinClient;
            default: throw new RuntimeException("can't find client");
        }
    }
}
