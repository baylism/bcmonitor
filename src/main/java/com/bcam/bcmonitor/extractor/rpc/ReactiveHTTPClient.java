package com.bcam.bcmonitor.extractor.rpc;

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
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeFilterFunctions;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec;
import reactor.core.publisher.Mono;

public class ReactiveHTTPClient {

    private Logger logger;
    private WebClient client;

    public ReactiveHTTPClient(String hostName, int port, String userName, String password) {

        logger = LoggerFactory.getLogger(ReactiveHTTPClient.class);
        ObjectMapper mapper = buildMapper();
        ExchangeStrategies strategies = buildStrategies(mapper);

        client = WebClient.builder()
                .baseUrl("http://" + hostName + ':' + port)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE)
                .filter(ExchangeFilterFunctions.basicAuthentication(userName, password))
                .filter(logRequest())
                .filter(logResponse())
                .exchangeStrategies(strategies)
                .build();
    }

    // constructor helpers
    private ObjectMapper buildMapper() {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(BitcoinBlock.class, new DashBlockDeserializer());
        mapper.registerModule(module);

        return mapper;
    }

    private ExchangeStrategies buildStrategies(ObjectMapper decoder) {

        return ExchangeStrategies
                .builder()
                .codecs(clientDefaultCodecsConfigurer -> clientDefaultCodecsConfigurer
                        .defaultCodecs()
                        .jackson2JsonDecoder(new Jackson2JsonDecoder(decoder, MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN, MediaType.TEXT_HTML))
                )
                .build();
    }

    // logging
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


    // requests
    public ResponseSpec requestResponseSpec(String JSONRequest) {

        System.out.println("CLIENT ABOUT TO POST");

        return client.post()
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(JSONRequest))
                .retrieve();
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
