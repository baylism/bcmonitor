package com.bcam.bcmonitor.extractor.rpc;

import com.bcam.bcmonitor.model.RPCResult;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    private ObjectMapper mapper;

    /**
     * Creates an HTTP client with a authentication and a custom Jackson deserializer
     */
    public ReactiveHTTPClient(String hostName,
                              int port,
                              String userName,
                              String password,
                              ObjectMapper mapper) {

        // this.mapper = mapper;
        ExchangeStrategies strategies = buildStrategies(mapper);


        client = WebClient.builder()
                .baseUrl("http://" + hostName + ':' + port)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE)
                .filter(ExchangeFilterFunctions.basicAuthentication(userName, password))
                .filter(logRequest())
                .filter(logResponse())
                .exchangeStrategies(strategies)
                .build();

        logger = LoggerFactory.getLogger(ReactiveHTTPClient.class);
    }

    /**
     * Creates an HTTP client with authentication and default Jackson serializers
     */
    public ReactiveHTTPClient(String hostName,
                              int port,
                              String userName,
                              String password) {

        client = WebClient.builder()
                .baseUrl("http://" + hostName + ':' + port)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE)
                .filter(ExchangeFilterFunctions.basicAuthentication(userName, password))
                .filter(logRequest())
                .filter(logResponse())
                .build();

        logger = LoggerFactory.getLogger(ReactiveHTTPClient.class);
    }

    /**
     * Creates an HTTP client with default Jackson serializers
     */
    public ReactiveHTTPClient(String hostName,
                              int port) {

        client = WebClient.builder()
                .baseUrl("http://" + hostName + ':' + port)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE)
                 .filter(logRequest())
                .filter(logResponse())
                .build();

        logger = LoggerFactory.getLogger(ReactiveHTTPClient.class);
    }

    // constructor helpers
    private ExchangeStrategies buildStrategies(ObjectMapper mapper) {

        return ExchangeStrategies
                .builder()
                .codecs(clientDefaultCodecsConfigurer -> clientDefaultCodecsConfigurer
                        .defaultCodecs()
                        .jackson2JsonDecoder(new Jackson2JsonDecoder(mapper, MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN, MediaType.TEXT_HTML))
                    )
                // .codecs(clientCodecConfigurer -> clientCodecConfigurer
                //         .registerDefaults(false))
                .build();
    }

    // logging
    private ExchangeFilterFunction logRequest() {
        return (clientRequest, next) -> {
            logger.info("Request: {} {}", clientRequest.method(), clientRequest.url());
            // clientRequest.headers()
            //         .forEach((name, values) -> values.forEach(value -> logger.info("{}={}", name, value)));
            logger.info("Attrs: " + clientRequest.attributes());
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


    // GET requests
    public ResponseSpec getResponseSpec(String blockchain, String method, String param) {

        String URIPath = String.join("/", blockchain, method, param);
        return client.get()
                .uri(URIPath)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve();


    }

    public ResponseSpec getResponseSpec(String blockchain, String method) {

        String URIPath = String.join("/", blockchain, method);

        return client.get()
                .uri(URIPath)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve();
    }

    public Mono<String> getString(String blockchain, String method) {

        String URIPath = String.join("/", blockchain, method);

        return client.get()
                .uri(URIPath)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class);
    }


    // POST requests
    public ResponseSpec requestResponseSpec(String JSONRequest) {

        return client.post()
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(JSONRequest))
                .retrieve();
    }

    public Mono<String> requestString(String JSONRequest) {

        return client.post()
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(JSONRequest))
                .retrieve()
                .bodyToMono(RPCResult.class)
                .map(RPCResult::toString);

        // return mapper.readValue(RPCResponse.flatMap());
    }
}
