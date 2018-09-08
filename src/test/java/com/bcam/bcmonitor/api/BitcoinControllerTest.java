package com.bcam.bcmonitor.api;

import com.bcam.bcmonitor.BitcoinRPCResponses;
import com.bcam.bcmonitor.model.*;
import com.bcam.bcmonitor.storage.BlockRepository;
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
import org.springframework.context.annotation.Configuration;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

@AutoConfigureWebTestClient
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {
        "BITCOIN_HOSTNAME=localhost", "BITCOIN_PORT=9998", "BITCOIN_UN=bitcoinuser1", "BITCOIN_PW=password", "DASH_HOSTNAME=localhost", "DASH_PORT=9998", "DASH_UN=dashuser1", "DASH_PW=password", "ZCASH_HOSTNAME=localhost", "ZCASH_PORT=9998", "ZCASH_UN=dashuser1", "ZCASH_PW=password","MONERO_HOSTNAME=localhost", "MONERO_PORT=9998", "MONERO_UN=dashuser1", "MONERO_PW=password", "MONGO_PW=foo"})
public class BitcoinControllerTest {

    private static final Logger logger = LoggerFactory.getLogger(BitcoinControllerTest.class);

    @Autowired
    BlockRepository<BitcoinBlock> blockRepository;


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
    public void getBlockFromRepository() {

        logger.info("TEST REPOSITORY IS " + blockRepository);

        BitcoinBlock expectedBlock = new BitcoinBlock();

        expectedBlock.setHash("00000000839a8e6886ab5951d76f411475428afc90947ee320161bbf18eb6048");

        ArrayList<String> txids = new ArrayList<>();
        txids.add("txid in the repository block");
        expectedBlock.setTxids(txids);

        blockRepository.save(expectedBlock).block();

        Mono<BitcoinBlock> insertedBlockMono = blockRepository.findById("00000000839a8e6886ab5951d76f411475428afc90947ee320161bbf18eb6048");

        StepVerifier
                .create(insertedBlockMono)
                .assertNext(insertedBlock -> {
                    assertEquals("00000000839a8e6886ab5951d76f411475428afc90947ee320161bbf18eb6048", insertedBlock.getHash());
                    assertEquals(txids, insertedBlock.getTxids());
                })
                .expectComplete()
                .verify();


        mockServer
                .when(
                        request()
                                .withMethod("POST")
                                .withBody("{\"jsonrpc\":\"jsonrpc\",\"id\":\"optional_string\",\"method\":\"getblock\",\"params\":[\"00000000839a8e6886ab5951d76f411475428afc90947ee320161bbf18eb6048\",true]}")
                )
                .respond(
                        response()
                                .withBody(BitcoinRPCResponses.validBlockResponse)
                                .withHeader("Content-Type", "text/html")
                );

        webTestClient
                .get()
                .uri("/api/bitcoin/block/00000000839a8e6886ab5951d76f411475428afc90947ee320161bbf18eb6048")
                .exchange()
                .expectStatus().isOk()
                .expectBody(BitcoinBlock.class)
                .consumeWith(result -> {
                    logger.info("Result in testwebclient is " + result.getResponseBody());
                    assertEquals(txids, result.getResponseBody().getTxids());
                });

        blockRepository.deleteAll().block();
    }


    @Test
    public void getBlock() {
        BitcoinBlock expectedBlock = new BitcoinBlock();
        expectedBlock.setHash("00000000839a8e6886ab5951d76f411475428afc90947ee320161bbf18eb6048");

        // TODO add additional assetions to check these
        expectedBlock.setHeight(0);
        expectedBlock.setTimeStamp(1390095618);
        expectedBlock.setSizeBytes(306);
        expectedBlock.setDifficulty(BigDecimal.valueOf(0.000244140625));
        expectedBlock.setMedianTime(1390095618);
        expectedBlock.setChainWork(BigInteger.valueOf(1048592L));
        expectedBlock.setConfirmations(1);


        ArrayList<String> txids = new ArrayList<>();
        txids.add("0e3e2357e806b6cdb1f70b54c3a3a17b6714ee1f0e68bebb44a74b1efd512098");
        expectedBlock.setTxids(txids);


        mockServer
                .when(
                        request()
                                .withMethod("POST")
                                .withBody("{\"jsonrpc\":\"jsonrpc\",\"id\":\"optional_string\",\"method\":\"getblock\",\"params\":[\"hash\",true]}")
                )
                .respond(
                        response()
                                .withBody(BitcoinRPCResponses.validBlockResponse)
                                .withHeader("Content-Type", "text/html")
                );

        webTestClient
                .get()
                .uri("/api/bitcoin/block/hash")
                .exchange()
                .expectStatus().isOk()
                .expectBody(BitcoinBlock.class).isEqualTo(expectedBlock)
                .consumeWith(result -> {
                    Assertions.assertIterableEquals(result.getResponseBody().getTxids(), expectedBlock.getTxids());
                });
    }

    @Test
    public void getTransaction() {

        mockServer
                .when(request()
                        .withMethod("POST")
                        .withBody("{\"jsonrpc\":\"jsonrpc\",\"id\":\"optional_string\",\"method\":\"getrawtransaction\",\"params\":[\"4a4373359f0d2f423d40d9124b100a0917ded551351d7d90e79f5cef90404194\",true]}") //TODO check bool encoding
                )
                .respond(
                        response()
                                .withBody(BitcoinRPCResponses.getTransactionResponse)
                                .withHeader("Content-Type", "text/html")
                );

        BitcoinTransaction expectedTransaction = new BitcoinTransaction();
        expectedTransaction.setHash("4a4373359f0d2f423d40d9124b100a0917ded551351d7d90e79f5cef90404194");


        webTestClient
                .get()
                .uri("/api/bitcoin/transaction/4a4373359f0d2f423d40d9124b100a0917ded551351d7d90e79f5cef90404194")
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
                                .withBody(BitcoinRPCResponses.getBlockchainInfoResponsePretty)
                                .withHeader("Content-Type", "text/html")
                );

        BlockchainInfo expectedResult = new BlockchainInfo();
        expectedResult.setBlocks(531489L);


        webTestClient
                .get()
                .uri("/api/bitcoin/blockchaininfo")
                .exchange()
                .expectStatus().isOk()
                .expectBody(BlockchainInfo.class)
                .consumeWith(result -> {
                    Assertions.assertEquals(result.getResponseBody().getBlocks(), 531489L);
                    Assertions.assertEquals(result.getResponseBody().getBestblockhash(), "0000000000000000003092e0372f341f5e027e026612b79d24558211eb486909");
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
                                .withBody(BitcoinRPCResponses.getBestBlockHashResponse)
                                .withHeader("Content-Type", "text/html")
                );

        webTestClient
                .get()
                .uri("/api/bitcoin/bestblockhash")
                .exchange()
                .expectStatus().isOk()
                // .expectBody(String.class).isEqualTo(BitcoinRPCResponses.getBestBlockHashResponse);
                .expectBody(String.class).isEqualTo("00000000000000000024c244f9c7d1cc0e593a7a4aa31c1ee2ef35206934bfff");
    }

    @Test
    public void getBlockHash() {

        mockServer
                .when(request()
                        .withMethod("POST")
                        .withBody("{\"jsonrpc\":\"jsonrpc\",\"id\":\"optional_string\",\"method\":\"getblockhash\",\"params\":[0]}")
                )
                .respond(
                        response()
                                .withBody(BitcoinRPCResponses.getBlockHashResponse)
                                .withHeader("Content-Type", "text/html")
                );

        webTestClient
                .get()
                .uri("/api/bitcoin/blockhash/0")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).isEqualTo("000000000019d6689c085ae165831e934ff763ae46a2a6c172b3f1b60a8ce26f");
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
                                .withBody(BitcoinRPCResponses.getMempoolResponse)
                                .withHeader("Content-Type", "text/html")
                );

        TransactionPool expectedPool = new TransactionPool();
        expectedPool.addTransaction("5bc76af67921c657c1c321de16a0403671365c6c07376a814b5de28c02ebe09b");
        expectedPool.addTransaction("22121a969bd36559f38eb4d01850d044b34d5d75d912ba10969e4f64fd345be9");
        expectedPool.addTransaction("24219415fc41d5205c455b3e1aef2b9323c3a2f5bc82eb2d08c1ea4336b3d3e5");
        expectedPool.addTransaction("4607202d56516e4f10af26ada8f210c4802e195e8d43e5ef7f7470d2d0171c9c");

        webTestClient
                .get()
                .uri("/api/bitcoin/transactionpool")
                .exchange()
                .expectStatus().isOk()
                .expectBody(TransactionPool.class).isEqualTo(expectedPool);
    }


    @WithMockUser
    @Test
    public void getCustomResponse() {
        mockServer
                .when(request()
                        .withMethod("POST")
                        .withBody("{\"jsonrpc\":\"jsonrpc\",\"id\":\"optional_string\",\"method\":\"getbestblockhash\",\"params\":[]}")
                )
                .respond(
                        response()
                                .withBody(BitcoinRPCResponses.getBestBlockHashResponse)
                                .withHeader("Content-Type", "text/html")
                );

        webTestClient
                .get()
                .uri("/api/bitcoin/method/getbestblockhash")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).isEqualTo("00000000000000000024c244f9c7d1cc0e593a7a4aa31c1ee2ef35206934bfff");
    }

    @WithMockUser
    @Test
    public void getCustomResponseWithOneParamString() {
        mockServer
                .when(request()
                        .withMethod("POST")
                        .withBody("{\"jsonrpc\":\"jsonrpc\",\"id\":\"optional_string\",\"method\":\"getblockhash\",\"params\":[\"2a\"]}")
                )
                .respond(
                        response()
                                .withBody(BitcoinRPCResponses.validBlockResponse)
                                .withHeader("Content-Type", "text/html")
                );

        webTestClient
                .get()
                .uri("/api/bitcoin/method/getblockhash/2a")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .consumeWith(result -> {
                    Assertions.assertTrue(result.getResponseBody().startsWith("{\"hash\""));
                });
    }

    @WithMockUser
    @Test
    public void getCustomResponseWithOneParamInt() {
        mockServer
                .when(request()
                        .withMethod("POST")
                        .withBody("{\"jsonrpc\":\"jsonrpc\",\"id\":\"optional_string\",\"method\":\"getblockhash\",\"params\":[2]}")
                )
                .respond(
                        response()
                                .withBody(BitcoinRPCResponses.validBlockResponse)
                                .withHeader("Content-Type", "text/html")
                );

        webTestClient
                .get()
                .uri("/api/bitcoin/method/getblockhash/2")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .consumeWith(result -> {
                    Assertions.assertTrue(result.getResponseBody().startsWith("{\"hash\""));
                });
    }


    @WithMockUser
    @Test
    public void getCustomResponseWithTwoParamSecondInt() {
        mockServer
                .when(request()
                        .withMethod("POST")
                        .withBody("{\"jsonrpc\":\"jsonrpc\",\"id\":\"optional_string\",\"method\":\"getblock\",\"params\":[\"00000000839a8e6886ab5951d76f411475428afc90947ee320161bbf18eb6048\",2]}")
                )
                .respond(
                        response()
                                .withBody(BitcoinRPCResponses.validBlockResponse)
                                .withHeader("Content-Type", "text/html")
                );

        webTestClient
                .get()
                .uri("/api/bitcoin/method/getblock/00000000839a8e6886ab5951d76f411475428afc90947ee320161bbf18eb6048/2")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .consumeWith(result -> {
                    Assertions.assertTrue(result.getResponseBody().startsWith("{\"hash\""));
                });
    }

    @WithMockUser
    @Test
    public void getCustomResponseWithTwoParamSecondString() {
        mockServer
                .when(request()
                        .withMethod("POST")
                        .withBody("{\"jsonrpc\":\"jsonrpc\",\"id\":\"optional_string\",\"method\":\"getblock\",\"params\":[\"00000000839a8e6886ab5951d76f411475428afc90947ee320161bbf18eb6048\",\"abs8921\"]}")
                )
                .respond(
                        response()
                                .withBody(BitcoinRPCResponses.validBlockResponse)
                                .withHeader("Content-Type", "text/html")
                );

        webTestClient
                .get()
                .uri("/api/bitcoin/method/getblock/00000000839a8e6886ab5951d76f411475428afc90947ee320161bbf18eb6048/abs8921")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .consumeWith(result -> {
                    Assertions.assertTrue(result.getResponseBody().startsWith("{\"hash\""));
                });
    }

    @Test
    public void getCustomResponseUnAuth() {

        webTestClient
                .get()
                .uri("/api/bitcoin/method/getbestblockhash")
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @Test
    public void getCustomResponseWithOneParamStringUnAuth() {

        webTestClient
                .get()
                .uri("/api/bitcoin/method/getblockhash/2a")
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @Test
    public void getCustomResponseWithOneParamIntUnAuth() {


        webTestClient
                .get()
                .uri("/api/bitcoin/method/getblockhash/2")
                .exchange()
                .expectStatus().isUnauthorized();
    }


    @Test
    public void getCustomResponseWithTwoParamSecondIntUnAuth() {

        webTestClient
                .get()
                .uri("/api/bitcoin/method/getblock/00000000839a8e6886ab5951d76f411475428afc90947ee320161bbf18eb6048/2")
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @Test
    public void getCustomResponseWithTwoParamSecondStringUnAuth() {

        webTestClient
                .get()
                .uri("/api/bitcoin/method/getblock/00000000839a8e6886ab5951d76f411475428afc90947ee320161bbf18eb6048/abs8921")
                .exchange()
                .expectStatus().isUnauthorized();
    }

}