package com.bcam.bcmonitor.extractor.client;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

@AutoConfigureWebTestClient
@RunWith(SpringRunner.class)
//  We create a `@SpringBootTest`, starting an actual server on a `RANDOM_PORT`
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReactiveBitcoinClientTest {

    // Spring Boot will create a `WebTestClient` for you,
    // already configure and ready to issue requests against "localhost:RANDOM_PORT"
    @Autowired
    private WebTestClient webTestClient;


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


    // @Test
    // public void testHello() {
    //     webTestClient
    //             // Create a GET request to test an endpoint
    //             .post()
    //             .body()
    //             .accept(MediaType.TEXT_PLAIN)
    //             .exchange()
    //             // and use the dedicated DSL to test assertions against the response
    //             .expectStatus().isOk()
    //             .expectBody(String.class).isEqualTo("Hello, Spring!");
    // }
}