package com.bcam.bcmonitor.api;

import com.bcam.bcmonitor.DashRPCResponses;
import com.bcam.bcmonitor.model.BitcoinBlock;
import com.bcam.bcmonitor.model.BitcoinTransaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockserver.integration.ClientAndServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

@AutoConfigureWebTestClient
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {
        "BITCOIN_HOSTNAME=localhost", "BITCOIN_PORT=9998", "BITCOIN_UN=bitcoinuser1", "BITCOIN_PW=password", "DASH_HOSTNAME=localhost", "DASH_PORT=9998", "DASH_UN=dashuser1", "DASH_PW=password", "ZCASH_HOSTNAME=localhost", "ZCASH_PORT=9998", "ZCASH_UN=dashuser1", "ZCASH_PW=password"})
public class DashControllerTest {
    @Autowired
    private WebTestClient webTestClient;

    private ClientAndServer mockServer;

    @Before
    public void startServer() {
        mockServer = startClientAndServer(9998);
    }

    @After
    public void stopServer() {
        mockServer.stop();
    }


    @Test
    public void getBlock() {

        mockServer
                .when(
                        request()
                                .withMethod("POST")
                                .withBody("{\"jsonrpc\":\"jsonrpc\",\"id\":\"optional_string\",\"method\":\"getblock\",\"params\":[\"000000000000003941fb8b64f23b1dc0391892c87dd8054a1f262b70203b2582\",2]}")
                )
                .respond(
                        response()
                                .withBody(DashRPCResponses.getBlockResponse)
                                .withHeader("Content-Type", "text/html")
                );

        BitcoinBlock expectedBlock = new BitcoinBlock();
        expectedBlock.setHash("000000000000003941fb8b64f23b1dc0391892c87dd8054a1f262b70203b2582");

        webTestClient
                .get()
                .uri("/api/dash/block/000000000000003941fb8b64f23b1dc0391892c87dd8054a1f262b70203b2582")
                .exchange()
                .expectStatus().isOk()
                .expectBody(BitcoinBlock.class).isEqualTo(expectedBlock);
    }

    @Test
    public void getTransaction() {

        mockServer
                .when(request()
                        .withMethod("POST")
                        .withBody("{\"jsonrpc\":\"jsonrpc\",\"id\":\"optional_string\",\"method\":\"getrawtransaction\",\"params\":[\"56b4880a119776d820347efb197a0cb040c30f54591a60fb9094a5ca660b364a\",true]}") //TODO check bool encoding
                )
                .respond(
                        response()
                                .withBody(DashRPCResponses.getTransactionResponse)
                                .withHeader("Content-Type", "text/html")
                );

        BitcoinTransaction expectedTransaction = new BitcoinTransaction();
        expectedTransaction.setHash("56b4880a119776d820347efb197a0cb040c30f54591a60fb9094a5ca660b364a");

        webTestClient
                .get()
                .uri("/api/dash/transaction/56b4880a119776d820347efb197a0cb040c30f54591a60fb9094a5ca660b364a")
                .exchange()
                .expectStatus().isOk()
                .expectBody(BitcoinTransaction.class).isEqualTo(expectedTransaction);
    }

    @Test
    public void getBlockchainInfo() {

        mockServer
                .when(request()
                        .withMethod("POST")
                        .withBody("{\"jsonrpc\":\"jsonrpc\",\"id\":\"optional_string\",\"method\":\"getblockchaininfo\",\"params\":[]}")
                )
                .respond(
                        response()
                                .withBody(DashRPCResponses.getBlockchainInfoResponse)
                                .withHeader("Content-Type", "text/html")
                );


        webTestClient
                .get()
                .uri("/api/dash/blockchaininfo")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).isEqualTo(DashRPCResponses.getBlockchainInfoResponse);
    }

    @Test
    public void getBestBlockHash() {

        mockServer
                .when(request()
                        .withMethod("POST")
                        .withBody("{\"jsonrpc\":\"jsonrpc\",\"id\":\"optional_string\",\"method\":\"getbestblockhash\",\"params\":[]}")
                )
                .respond(
                        response()
                                .withBody(DashRPCResponses.getBestBlockHashResponse)
                                .withHeader("Content-Type", "text/html")
                );

        webTestClient
                .get()
                .uri("/api/dash/bestblockhash")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).isEqualTo(DashRPCResponses.getBestBlockHashResponse);
        // .expectBody(String.class).isEqualTo("00000000000000000024c244f9c7d1cc0e593a7a4aa31c1ee2ef35206934bfff");
    }




}