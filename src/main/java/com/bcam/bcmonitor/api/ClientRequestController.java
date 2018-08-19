package com.bcam.bcmonitor.api;

import com.bcam.bcmonitor.extractor.client.ReactiveBitcoinClient;
import com.bcam.bcmonitor.extractor.client.ReactiveDashClient;
import com.bcam.bcmonitor.extractor.client.ReactiveZCashClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class ClientRequestController {

    private ReactiveZCashClient zCashClient;
    private ReactiveDashClient dashClient;
    private ReactiveBitcoinClient bitcoinClient;

    @Autowired
    public ClientRequestController(ReactiveZCashClient zCashClient, ReactiveDashClient dashClient, ReactiveBitcoinClient bitcoinClient) {
        this.zCashClient = zCashClient;
        this.dashClient = dashClient;
        this.bitcoinClient = bitcoinClient;
    }

    // ============ client provided requests ============
    @GetMapping("/{blockchain}/raw/{jsonQuery}")
    Mono<String> getRawResponse(@PathVariable String blockchain, @PathVariable String jsonQuery) {

        switch (blockchain) {
            case "dash" : return dashClient.getRawResponse(jsonQuery);
            case "zcash" : return zCashClient.getRawResponse(jsonQuery);
            case "bitcoin" : return bitcoinClient.getRawResponse(jsonQuery);
            default: throw new RuntimeException("can't find client");
        }
    }

    @GetMapping("/{blockchain}/method/{methodName}")
    Mono<String> getCustomResponse(@PathVariable String blockchain, @PathVariable String methodName) {

        switch (blockchain) {
            case "dash" : return dashClient.getCustomResponse(methodName);
            case "zcash" : return zCashClient.getCustomResponse(methodName);
            case "bitcoin" : return bitcoinClient.getCustomResponse(methodName);
            default: throw new RuntimeException("can't find client");
        }
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

    private ReactiveBitcoinClient getClient(String blockchain) {
        switch (blockchain) {
            case "dash" : return dashClient;
            case "zcash" : return zCashClient;
            case "bitcoin" : return bitcoinClient;
            default: throw new RuntimeException("can't find client");
        }
    }
    // private Mono<String> handler(String blockchain, String param) {
    //
    //     switch (blockchain) {
    //         case "dash" : return dashClient.getCustomResponse(args);
    //         case "zcash" : return zCashClient.getCustomResponse(args);
    //         case "bitcoin" : return bitcoinClient.getCustomResponse(args);
    //         default: throw new RuntimeException("can't find client");
    //     }
    // }
}
