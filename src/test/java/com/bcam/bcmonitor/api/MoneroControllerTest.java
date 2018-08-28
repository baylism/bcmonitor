package com.bcam.bcmonitor.api;

import com.bcam.bcmonitor.MoneroRPCResponses;
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
        "BITCOIN_HOSTNAME=localhost", "BITCOIN_PORT=9998", "BITCOIN_UN=bitcoinuser1", "BITCOIN_PW=password", "DASH_HOSTNAME=foobar", "DASH_PORT=9998", "DASH_UN=dashuser1", "DASH_PW=password", "ZCASH_HOSTNAME=localhost", "ZCASH_PORT=9998", "ZCASH_UN=dashuser1", "ZCASH_PW=password","MONERO_HOSTNAME=localhost", "MONERO_PORT=9998", "MONERO_UN=dashuser1", "MONERO_PW=password"})
public class MoneroControllerTest {

    private static final Logger logger = LoggerFactory.getLogger(MoneroControllerTest.class);


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
                                .withBody("{\"jsonrpc\":\"jsonrpc\",\"id\":\"optional_string\",\"method\":\"get_block\",\"params\":{\"hash\":\"510ee3c4e14330a7b96e883c323a60ebd1b5556ac1262d0bc03c24a3b785516f\"}}")
                )
                .respond(
                        response()
                                .withBody(MoneroRPCResponses.getBlockResponse)
                                .withHeader("Content-Type", "text/html")
                );

        MoneroBlock expectedBlock = new MoneroBlock();
        expectedBlock.setHash("510ee3c4e14330a7b96e883c323a60ebd1b5556ac1262d0bc03c24a3b785516f");
        expectedBlock.setHeight(993056L);
        //
        ArrayList<String> tx = new ArrayList<>(Arrays.asList("79c6b9f00db027bde151705aafe85c495883aae2597d5cb8e1adb2e0f3ae1d07", "d715db73331abc3ec588ef07c7bb195786a4724b08dff431b51ffa32a4ce899b", "b197066426c0ed89f0b431fe171f7fd62bc95dd29943daa7cf3585cf1fdfc99d"));

        // TODO miner tx hashes

        webTestClient
                .get()
                .uri("/api/monero/block/510ee3c4e14330a7b96e883c323a60ebd1b5556ac1262d0bc03c24a3b785516f")
                .exchange()
                .expectStatus().isOk()
                .expectBody(MoneroBlock.class).isEqualTo(expectedBlock)
                .consumeWith(result -> {
                    logger.info("Monero getblock transactions: " + result.getResponseBody().getTxids());
                    Assertions.assertIterableEquals(tx, result.getResponseBody().getTxids());
                    Assertions.assertEquals(1457720227L, result.getResponseBody().getTimeStamp());
                });
    }
    //
    // @Test
    // public void getTransaction() {
    //
    //     mockServer
    //             .when(request()
    //                     .withMethod("POST")
    //                     .withBody("{\"txs_hashes\":[\"d6e48158472848e6687173a91ae6eebfa3e1d778e65252ee99d7515d63090408\"]}")
    //             )
    //             .respond(
    //                     response()
    //                             .withBody(MoneroRPCResponses.getTransactionResponse)
    //                             .withHeader("Content-Type", "text/html")
    //             );
    //
    //     MoneroTransaction expectedTransaction = new MoneroTransaction();
    //     expectedTransaction.setHash("d6e48158472848e6687173a91ae6eebfa3e1d778e65252ee99d7515d63090408");
    //
    //     webTestClient
    //             .get()
    //             .uri("/api/monero/transaction/d6e48158472848e6687173a91ae6eebfa3e1d778e65252ee99d7515d63090408")
    //             .exchange()
    //             .expectStatus().isOk()
    //             .expectBody(MoneroTransaction.class).isEqualTo(expectedTransaction);
    // }
    //
    // @Test
    // public void getBlockchainInfo() {
    //
    //     mockServer
    //             .when(request()
    //                     .withMethod("POST")
    //                     .withBody("{\"jsonrpc\": \"1.0\", \"id\":\"curltest\", \"method\": \"get_info\", \"params\": [] }")
    //             )
    //             .respond(
    //                     response()
    //                             .withBody(MoneroRPCResponses.getBlockchainInfoResponse)
    //                             .withHeader("Content-Type", "text/html")
    //             );
    //
    //
    //     webTestClient
    //             .get()
    //             .uri("/api/monero/blockchaininfo")
    //             .exchange()
    //             .expectStatus().isOk()
    //             .expectBody(BlockchainInfo.class)
    //             .consumeWith(result -> {
    //                 Assertions.assertEquals(result.getResponseBody().getBlocks(), 1648775L);
    //                 Assertions.assertEquals(result.getResponseBody().getBestblockhash(), "4d543d125b181cf62ab3b7c2562aca3a4c11fa4bd3eb2ab6fdd581545f8d226b");
    //             });
    // }
    //
    // @Test
    // public void getBestBlockHash() {
    //
    //     mockServer
    //             .when(request()
    //                     .withMethod("POST")
    //                     .withBody("{\"jsonrpc\": \"1.0\", \"id\":\"curltest\", \"method\": \"get_last_block_header\", \"params\": [] }")
    //             )
    //             .respond(
    //                     response()
    //                             .withBody(MoneroRPCResponses.getBestBlockHashResponse)
    //                             .withHeader("Content-Type", "text/html")
    //             );
    //
    //     webTestClient
    //             .get()
    //             .uri("/api/monero/bestblockhash")
    //             .exchange()
    //             .expectStatus().isOk()
    //             .expectBody(String.class).isEqualTo("4d543d125b181cf62ab3b7c2562aca3a4c11fa4bd3eb2ab6fdd581545f8d226b");
    // }
    //
    // @Test
    // public void getTransactionPool() {
    //
    //     mockServer
    //             .when(request()
    //                     .withMethod("POST")
    //                     .withBody("")
    //             )
    //             .respond(
    //                     response()
    //                             .withBody(MoneroRPCResponses.getMempoolResponse)
    //                             .withHeader("Content-Type", "text/html")
    //             );
    //
    //     TransactionPool expectedPool = new TransactionPool();
    //     expectedPool.addTransaction("5bc76af67921c657c1c321de16a0403671365c6c07376a814b5de28c02ebe09b");
    //     expectedPool.addTransaction("22121a969bd36559f38eb4d01850d044b34d5d75d912ba10969e4f64fd345be9");
    //     expectedPool.addTransaction("24219415fc41d5205c455b3e1aef2b9323c3a2f5bc82eb2d08c1ea4336b3d3e5");
    //     expectedPool.addTransaction("4607202d56516e4f10af26ada8f210c4802e195e8d43e5ef7f7470d2d0171c9c");
    //
    //     webTestClient
    //             .get()
    //             .uri("/api/monero/transactionpool")
    //             .exchange()
    //             .expectStatus().isOk()
    //             .expectBody(TransactionPool.class).isEqualTo(expectedPool);
    // }
    //
    // @Test
    // public void getTransactionPoolInfo() {
    //
    //     mockServer
    //             .when(request()
    //                     .withMethod("POST")
    //                     .withBody("")
    //             )
    //             .respond(
    //                     response()
    //                             .withBody(MoneroRPCResponses.getGetMempoolInfoResponse)
    //                             .withHeader("Content-Type", "text/html")
    //             );
    //
    //     webTestClient
    //             .get()
    //             .uri("/api/monero/transactionpoolinfo")
    //             .exchange()
    //             .expectStatus().isOk()
    //             .expectBody(TransactionPoolInfo.class)
    //             .consumeWith(result -> {
    //                 TransactionPoolInfo res = result.getResponseBody();
    //                 Assertions.assertEquals(2, res.getSize());
    //                 Assertions.assertEquals(13304 ,res.getSizeBytes());
    //                 Assertions.assertEquals(28960, res.getMemoryUsage());
    //             });
    // }
}