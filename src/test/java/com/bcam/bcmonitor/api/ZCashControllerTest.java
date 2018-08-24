package com.bcam.bcmonitor.api;

import com.bcam.bcmonitor.BitcoinRPCResponses;
import com.bcam.bcmonitor.ZCashRPCResponses;
import com.bcam.bcmonitor.model.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockserver.integration.ClientAndServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.ArrayList;
import java.util.Arrays;

import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

@AutoConfigureWebTestClient
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {
        "BITCOIN_HOSTNAME=localhost", "BITCOIN_PORT=9998", "BITCOIN_UN=bitcoinuser1", "BITCOIN_PW=password", "DASH_HOSTNAME=foobar", "DASH_PORT=9998", "DASH_UN=dashuser1", "DASH_PW=password", "ZCASH_HOSTNAME=localhost", "ZCASH_PORT=9998", "ZCASH_UN=dashuser1", "ZCASH_PW=password"})
public class ZCashControllerTest {
    private static final Logger logger = LoggerFactory.getLogger(ZCashController.class);


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
                                .withBody("{\"jsonrpc\":\"jsonrpc\",\"id\":\"optional_string\",\"method\":\"getblock\",\"params\":[\"0007bc227e1c57a4a70e237cad00e7b7ce565155ab49166bc57397a26d339283\",true]}")
                )
                .respond(
                        response()
                                .withBody(ZCashRPCResponses.getGetBlockResponse_New)
                                .withHeader("Content-Type", "text/html")
                );

        ZCashBlock expectedBlock = new ZCashBlock();
        expectedBlock.setHash("0000000000cc50281e133961f91dea67d6b4a9af68f5bf2bbf3504169dcd45b0");
        expectedBlock.setHeight(381560L);

        ArrayList<String> tx = new ArrayList<>(Arrays.asList("1342c6415d7db3768618dac1912777d20a30e0aed8350094cca5db72367b2398", "32d1a2067bb6aefe7fcc9ba203122946316c91d790fb6ff3cbf206f5eea5c740", "d7358b29a186c7d47e72994ee3d8721aa97b3c1cddd69a129861786f4fa170fb", "801beb6971bba5170ee62bca7d3a47e11a89ec77f026ff0dac1b030e9e348787", "6451bef531a88a3ff067d115d701a4a666413fb734925b7d7a84ea4c7ef731a5"));


        webTestClient
                .get()
                .uri("/api/zcash/block/0007bc227e1c57a4a70e237cad00e7b7ce565155ab49166bc57397a26d339283")
                .exchange()
                .expectStatus().isOk()
                .expectBody(ZCashBlock.class).isEqualTo(expectedBlock)
                .consumeWith(result -> {
                    logger.info("ZCash getblock transactions: " + result.getResponseBody().getTxids());
                    Assertions.assertIterableEquals(tx, result.getResponseBody().getTxids());
                    Assertions.assertEquals(1535108275L, result.getResponseBody().getTimeStamp());
                });
    }

    @Test
    public void getTransaction() {

        mockServer
                .when(request()
                        .withMethod("POST")
                        .withBody("{\"jsonrpc\":\"jsonrpc\",\"id\":\"optional_string\",\"method\":\"getrawtransaction\",\"params\":[\"851bf6fbf7a976327817c738c489d7fa657752445430922d94c983c0b9ed4609\",1]}") //TODO check bool encoding
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
                .expectBody(BlockchainInfo.class)
                .consumeWith(result -> {
                    Assertions.assertEquals(result.getResponseBody().getBlocks(), 378358L);
                    Assertions.assertEquals(result.getResponseBody().getBestblockhash(), "00000000008c9baaf66c73b434713306648da5653ab9afa659f970fa8619421c");
                    // Assertions.assertEquals(result.getResponseBody().getMediantime(), 1531318259L);
                });
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
                .expectBody(String.class).isEqualTo("00000000b0e31a8bb7bbcf902e7f854599e369984b9f85f2d2f1e3cfd9c0f265");
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

    @Test
    public void getTransactionPoolInfo() {

        mockServer
                .when(request()
                        .withMethod("POST")
                        .withBody("{\"jsonrpc\":\"jsonrpc\",\"id\":\"optional_string\",\"method\":\"getmempoolinfo\",\"params\":[]}")
                )
                .respond(
                        response()
                                .withBody(ZCashRPCResponses.getMempoolInfoResponse_New)
                                .withHeader("Content-Type", "text/html")
                );

        // "{\"result\":{\"size\":2,\"bytes\":13304,\"usage\":28960},\"error\":null,\"id\":\"curltest\"}";
        webTestClient
                .get()
                .uri("/api/zcash/transactionpoolinfo")
                .exchange()
                .expectStatus().isOk()
                .expectBody(TransactionPoolInfo.class)
                .consumeWith(result -> {
                    TransactionPoolInfo res = result.getResponseBody();
                    Assertions.assertEquals(2, res.getSize());
                    Assertions.assertEquals(13304 ,res.getSizeBytes());
                    Assertions.assertEquals(28960, res.getMemoryUsage());
                });
    }
}