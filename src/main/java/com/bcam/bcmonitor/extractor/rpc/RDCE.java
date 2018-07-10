package com.bcam.bcmonitor.extractor.rpc;


public class RDCE extends ReactiveHTTPClientGeneric {

    // private ObjectMapper mapper;
    //
    // public RDCE() {
    //     logger = LoggerFactory.getLogger(RDCE.class);
    //     ExchangeStrategies strategies = buildStrategies(mapper);
    //     client = buildClient(strategies);
    // }
    //
    // // requests
    // public Mono<BitcoinBlock> request(String hash) {
    //     JSONRPCRequest request = new JSONRPCRequest("getblock");
    //     request.addParam(hash);
    //
    //     return client.post()
    //             .accept(MediaType.APPLICATION_JSON)
    //             .body(BodyInserters.fromObject(request.toString()))
    //             .retrieve()
    //             .bodyToMono(BitcoinBlock.class);
    // }


}
