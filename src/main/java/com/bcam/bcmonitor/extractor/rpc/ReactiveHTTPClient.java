package com.bcam.bcmonitor.extractor.rpc;

import com.bcam.bcmonitor.extractor.mapper.BitcoinBlockDeserializer;
import com.bcam.bcmonitor.extractor.mapper.DashBlockDeserializer;
import com.bcam.bcmonitor.model.BitcoinBlock;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.*;
import reactor.core.publisher.Mono;

import java.io.IOException;

public class ReactiveHTTPClient {


    Logger logger;

    private String validBlockResponse = "{\n" +
            "\t\"result\": {\n" +
            "\t\t\"hash\": \"00000000839a8e6886ab5951d76f411475428afc90947ee320161bbf18eb6048\",\n" +
            "\t\t\"confirmations\": 527114,\n" +
            "\t\t\"strippedsize\": 215,\n" +
            "\t\t\"size\": 215,\n" +
            "\t\t\"weight\": 860,\n" +
            "\t\t\"height\": 1,\n" +
            "\t\t\"version\": 1,\n" +
            "\t\t\"versionHex\": \"00000001\",\n" +
            "\t\t\"merkleroot\": \"0e3e2357e806b6cdb1f70b54c3a3a17b6714ee1f0e68bebb44a74b1efd512098\",\n" +
            "\t\t\"tx\": [\n" +
            "\t\t\t\"0e3e2357e806b6cdb1f70b54c3a3a17b6714ee1f0e68bebb44a74b1efd512098\"\n" +
            "\t\t],\n" +
            "\t\t\"time\": 1231469665,\n" +
            "\t\t\"mediantime\": 1231469665,\n" +
            "\t\t\"nonce\": 2573394689,\n" +
            "\t\t\"bits\": \"1d00ffff\",\n" +
            "\t\t\"difficulty\": 1,\n" +
            "\t\t\"chainwork\": \"0000000000000000000000000000000000000000000000000000000200020002\",\n" +
            "\t\t\"previousblockhash\": \"000000000019d6689c085ae165831e934ff763ae46a2a6c172b3f1b60a8ce26f\",\n" +
            "\t\t\"nextblockhash\": \"000000006a625f06636b8bb6ac7b960a8d03705d1ace08b1a19da3fdcc99ddbd\"\n" +
            "\t},\n" +
            "\t\"error\": null,\n" +
            "\t\"id\": null\n" +
            "}";

    private WebClient client;

    public ReactiveHTTPClient(String hostName, int port, String userName, String password) {

        logger = LoggerFactory.getLogger(ReactiveHTTPClient.class);

        // decoder objectmapper
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(BitcoinBlock.class, new DashBlockDeserializer());
        mapper.registerModule(module);



        // add custom deserializers
        ExchangeStrategies strategies = ExchangeStrategies
                .builder()
                .codecs(clientDefaultCodecsConfigurer -> {

                    // clientDefaultCodecsConfigurer
                    //         .defaultCodecs()
                    //         .jackson2JsonEncoder(new Jackson2JsonEncoder(new ObjectMapper()));

                    clientDefaultCodecsConfigurer
                            .defaultCodecs()
                            .jackson2JsonDecoder(new Jackson2JsonDecoder(mapper, MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN, MediaType.TEXT_HTML));

                }).build();


        client = WebClient.builder()
                .baseUrl("http://" + hostName + ':' + port)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE)
                .filter(ExchangeFilterFunctions.basicAuthentication(userName, password))
                .filter(logRequest())
                .filter(logResponse())
                .exchangeStrategies(strategies)
                .build();

    }

    private ExchangeFilterFunction logRequest() {
        return (clientRequest, next) -> {
            logger.info("Request: {} {}", clientRequest.method(), clientRequest.url());
            clientRequest.headers()
                    .forEach((name, values) -> values.forEach(value -> logger.info("{}={}", name, value)));
            logger.info(clientRequest.body().toString());
            return next.exchange(clientRequest);
        };
    }

    private ExchangeFilterFunction logResponse() {
        return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
            logger.info("Response Status {}", clientResponse.statusCode());
            logger.info("Response Content type {}", clientResponse.headers().contentType());
            return Mono.just(clientResponse);
        });
    }

    public Mono<BitcoinBlock> convert(String s) {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(BitcoinBlock.class, new BitcoinBlockDeserializer());
        mapper.registerModule(module);

        BitcoinBlock bb = new BitcoinBlock();
        try {
            bb = mapper.readValue(s, BitcoinBlock.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Mono.just(bb);
    }

    public Mono<BitcoinBlock> request(String JSONRequest) {

        System.out.println("CLIENT ABOUT TO POST");


        return client.post()
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(JSONRequest))
                .retrieve()
                .bodyToMono(BitcoinBlock.class);
    }


    // mapper can convert response to string
    public Mono<String> requestString(String JSONRequest) {

        System.out.println("CLIENT ABOUT TO POST");

        return client.post()
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(JSONRequest))
                .retrieve()
                .bodyToMono(String.class);
    }

    // // mapper can convert response to string
    // public Mono<String> requestString(String JSONRequest) {
    //
    //     System.out.println("CLIENT ABOUT TO POST");
    //
    //     return client.post()
    //             .accept(MediaType.APPLICATION_JSON)
    //             .body(BodyInserters.fromObject(JSONRequest))
    //             .retrieve()
    //             .bodyToMono(String.class);
    // }


    // mapper can convert hardcoded string to block
    public Mono<BitcoinBlock> req() throws IOException {

        System.out.println("CLIENT ABOUT TO POST");

        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(BitcoinBlock.class, new BitcoinBlockDeserializer());
        mapper.registerModule(module);

        BitcoinBlock bb = mapper.readValue(validBlockResponse, BitcoinBlock.class);

        return Mono.just(bb);
    }

    // private Mono<ClientResponse> result = client.get()
    //         .uri("/hello")
    //         .accept(MediaType.TEXT_PLAIN)
    //         .exchange();
    //
    // public String getResult() {
    //     return ">> result = " + result.flatMap(res -> res.bodyToMono(String.class)).block();

}
