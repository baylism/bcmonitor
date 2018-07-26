package com.bcam.bcmonitor.api;

import com.bcam.bcmonitor.ZCashRPCResponses;
import com.bcam.bcmonitor.model.BitcoinBlock;
import com.bcam.bcmonitor.model.TransactionPool;
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
                .expectBody(String.class);
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
                // .expectBody(String.class).isEqualTo(ZCashRPCResponses.getBestBlockHashResponse);
        .expectBody(String.class).isEqualTo("\"00000000b0e31a8bb7bbcf902e7f854599e369984b9f85f2d2f1e3cfd9c0f265\"");
    }

    @Test
    public void getTransactionPool() {

        mockServer
                .when(request()
                        .withMethod("POST")
                        .withBody("{\"jsonrpc\":\"jsonrpc\",\"id\":\"optional_string\",\"method\":\"getrawmempool\",\"params\":[]}")
                )
                .respond(
                        response()
                                .withBody(ZCashRPCResponses.getMempoolResponse)
                                .withHeader("Content-Type", "text/html")
                );

        TransactionPool expectedPool = new TransactionPool();
        expectedPool.addTransaction("5bc76af67921c657c1c321de16a0403671365c6c07376a814b5de28c02ebe09b");
        expectedPool.addTransaction("22121a969bd36559f38eb4d01850d044b34d5d75d912ba10969e4f64fd345be9");
        expectedPool.addTransaction("24219415fc41d5205c455b3e1aef2b9323c3a2f5bc82eb2d08c1ea4336b3d3e5");
        expectedPool.addTransaction("4607202d56516e4f10af26ada8f210c4802e195e8d43e5ef7f7470d2d0171c9c");

        webTestClient
                .get()
                .uri("/api/zcash/transactionpool")
                .exchange()
                .expectStatus().isOk()
                .expectBody(TransactionPool.class).isEqualTo(expectedPool);
    }
}