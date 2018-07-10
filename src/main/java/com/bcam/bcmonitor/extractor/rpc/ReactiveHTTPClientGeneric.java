package com.bcam.bcmonitor.extractor.rpc;

import org.slf4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class ReactiveHTTPClientGeneric  {


    protected Logger logger;
    protected WebClient client;

    private String hostName;
    private int port;
    private String userName;
    private String password;


    // public ReactiveHTTPClientGeneric() {
    //     logger = LoggerFactory.getLogger(ReactiveHTTPClient.class);
    //     ExchangeStrategies strategies = buildStrategies(mapper);
    //     client = buildClient(strategies);
    // }
    //
    // protected WebClient buildClient(ExchangeStrategies strategies) {
    //     return WebClient.builder()
    //             .baseUrl("http://" + hostName + ':' + port)
    //             .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE)
    //             .filter(ExchangeFilterFunctions.basicAuthentication(userName, password))
    //             .filter(logRequest())
    //             .filter(logResponse())
    //             .exchangeStrategies(strategies)
    //             .build();
    // }
    //
    // protected ExchangeStrategies buildStrategies(ObjectMapper decoder) {
    //
    //     return ExchangeStrategies
    //             .builder()
    //             .codecs(clientDefaultCodecsConfigurer -> clientDefaultCodecsConfigurer
    //                     .defaultCodecs()
    //                     .jackson2JsonDecoder(new Jackson2JsonDecoder(decoder, MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN, MediaType.TEXT_HTML))
    //             )
    //             .build();
    // }

    // logging
    // private ExchangeFilterFunction logRequest() {
    //     return (clientRequest, next) -> {
    //         logger.info("Request: {} {}", clientRequest.method(), clientRequest.url());
    //         clientRequest.headers()
    //                 .forEach((name, values) -> values.forEach(value -> logger.info("{}={}", name, value)));
    //         logger.info(clientRequest.body().toString());
    //         return next.exchange(clientRequest);
    //     };
    // }

    private ExchangeFilterFunction logResponse() {
        return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
            logger.info("Response Status {}", clientResponse.statusCode());
            logger.info("Response Content type {}", clientResponse.headers().contentType());
            return Mono.just(clientResponse);
        });
    }


    public Mono<String> requestString(String JSONRequest) {

        System.out.println("CLIENT ABOUT TO POST");

        return client.post()
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(JSONRequest))
                .retrieve()
                .bodyToMono(String.class);
    }
}

