package com.bcam.bcmonitor.api;

import com.bcam.bcmonitor.ZCashRPCResponses;
import com.bcam.bcmonitor.model.BitcoinBlock;
import com.bcam.bcmonitor.model.ZCashTransaction;
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
        "BITCOIN_HOSTNAME=localhost", "BITCOIN_PORT=9998", "BITCOIN_UN=bitcoinuser1", "BITCOIN_PW=password", "DASH_HOSTNAME=foobar", "DASH_PORT=9998", "DASH_UN=dashuser1", "DASH_PW=password", "ZCASH_HOSTNAME=localhost", "ZCASH_PORT=9998", "ZCASH_UN=dashuser1", "ZCASH_PW=password"})
public class ZCashControllerTest {

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
                                .withBody("{\"jsonrpc\":\"jsonrpc\",\"id\":\"optional_string\",\"method\":\"getblock\",\"params\":[\"0007bc227e1c57a4a70e237cad00e7b7ce565155ab49166bc57397a26d339283\",2]}")
                )
                .respond(
                        response()
                                .withBody(ZCashRPCResponses.getBlockResponse)
                                .withHeader("Content-Type", "text/html")
                );

        BitcoinBlock expectedBlock = new BitcoinBlock();
        expectedBlock.setHash("0007bc227e1c57a4a70e237cad00e7b7ce565155ab49166bc57397a26d339283");

        webTestClient
                .get()
                .uri("/api/zcash/block/0007bc227e1c57a4a70e237cad00e7b7ce565155ab49166bc57397a26d339283")
                .exchange()
                .expectStatus().isOk()
                .expectBody(BitcoinBlock.class).isEqualTo(expectedBlock);
    }

    @Test
    public void getTransaction() {

        mockServer
                .when(request()
                        .withMethod("POST")
                        .withBody("{\"jsonrpc\":\"jsonrpc\",\"id\":\"optional_string\",\"method\":\"getrawtransaction\",\"params\":[\"851bf6fbf7a976327817c738c489d7fa657752445430922d94c983c0b9ed4609\",true]}") //TODO check bool encoding
                )
                .respond(
                        response()
                                .withBody(ZCashRPCResponses.getTransactionResponse)
                                .withHeader("Content-Type", "text/html")
                );

        ZCashTransaction expectedTransaction = new ZCashTransaction();
        expectedTransaction.setHash("851bf6fbf7a976327817c738c489d7fa657752445430922d94c983c0b9ed4609");

        webTestClient
                .get()
                .uri("/api/zcash/transaction/851bf6fbf7a976327817c738c489d7fa657752445430922d94c983c0b9ed4609")
                .exchange()
                .expectStatus().isOk()
                .expectBody(ZCashTransaction.class).isEqualTo(expectedTransaction);
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
                                .withBody(ZCashRPCResponses.getBlockchainInfoResponse)
                                .withHeader("Content-Type", "text/html")
                );


        webTestClient
                .get()
                .uri("/api/zcash/blockchaininfo")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).isEqualTo(ZCashRPCResponses.getBlockchainInfoResponse);
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
                                .withBody(ZCashRPCResponses.getBestBlockHashResponse)
                                .withHeader("Content-Type", "text/html")
                );

        webTestClient
                .get()
                .uri("/api/zcash/bestblockhash")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).isEqualTo(ZCashRPCResponses.getBestBlockHashResponse);
        // .expectBody(String.class).isEqualTo("00000000000000000024c244f9c7d1cc0e593a7a4aa31c1ee2ef35206934bfff");
    }
}