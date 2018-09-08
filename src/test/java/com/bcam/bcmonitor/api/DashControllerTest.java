package com.bcam.bcmonitor.api;

import com.bcam.bcmonitor.BitcoinRPCResponses;
import com.bcam.bcmonitor.DashRPCResponses;
import com.bcam.bcmonitor.model.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockserver.integration.ClientAndServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.ArrayList;

import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

@AutoConfigureWebTestClient
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {
        "BITCOIN_HOSTNAME=localhost", "BITCOIN_PORT=9998", "BITCOIN_UN=bitcoinuser1", "BITCOIN_PW=password", "DASH_HOSTNAME=localhost", "DASH_PORT=9998", "DASH_UN=dashuser1", "DASH_PW=password", "ZCASH_HOSTNAME=localhost", "ZCASH_PORT=9998", "ZCASH_UN=dashuser1", "ZCASH_PW=password","MONERO_HOSTNAME=localhost", "MONERO_PORT=9998", "MONERO_UN=dashuser1", "MONERO_PW=password", "MONGO_PW=foo"})
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
                                .withBody("{\"jsonrpc\":\"jsonrpc\",\"id\":\"optional_string\",\"method\":\"getblock\",\"params\":[\"000000000000003941fb8b64f23b1dc0391892c87dd8054a1f262b70203b2582\",true]}")
                )
                .respond(
                        response()
                                .withBody(DashRPCResponses.getBlockResponse)
                                .withHeader("Content-Type", "text/html")
                );

        BitcoinBlock expectedBlock = new BitcoinBlock();
        expectedBlock.setHash("000000000000003941fb8b64f23b1dc0391892c87dd8054a1f262b70203b2582");

        ArrayList<String> txids = new ArrayList<>();
        // txids.add("0e3e2357e806b6cdb1f70b54c3a3a17b6714ee1f0e68bebb44a74b1efd512098");
        txids.add("d903e90e5745ca48a88c84d6f2e0431d49a27cfede8e6171c1df7ed6aa7747ed");
        txids.add("09c7ba9498aee4e662c914d9c806d759bf44957fe5f8d771d76e3f2405d28e80");
        txids.add("85cbbabe0adab7610c37341ec0f4615eea17e2f454f8c718bc1352224234a16d");
        txids.add("4607202d56516e4f10af26ada8f210c4802e195e8d43e5ef7f7470d2d0171c9c");
        expectedBlock.setTxids(txids);

        webTestClient
                .get()
                .uri("/api/dash/block/000000000000003941fb8b64f23b1dc0391892c87dd8054a1f262b70203b2582")
                .exchange()
                .expectStatus().isOk()
                .expectBody(BitcoinBlock.class).isEqualTo(expectedBlock)
                // .returnResult();
                .consumeWith(result -> {
                    Assertions.assertIterableEquals(result.getResponseBody().getTxids(), expectedBlock.getTxids());
                });
    }

    @Test
    public void getLatestBlock() {

        mockServer
                .when(
                        request()
                                .withMethod("POST")
                                .withBody("{\"jsonrpc\":\"jsonrpc\",\"id\":\"optional_string\",\"method\":\"getblock\",\"params\":[\"00000000000000427a9048cbb95c484afc559c01bc42eef505e075e5ef05c93f\",true]}")
                )
                .respond(
                        response()
                                .withBody(DashRPCResponses.getGetLatestBlockResponse)
                                .withHeader("Content-Type", "text/html")
                );

        BitcoinBlock expectedBlock = new BitcoinBlock();
        expectedBlock.setHash("00000000000000427a9048cbb95c484afc559c01bc42eef505e075e5ef05c93f");

        ArrayList<String> txids = new ArrayList<>();
        txids.add("809d92fcdec079782960449c15e759aeb6935f4a124f6bd699e104397b7a30b2");
        txids.add("25190c3e6b215542fa211d58bc483556b54b70e567d87b5b9e06c6bb16d79b6c");

        expectedBlock.setTxids(txids);

        webTestClient
                .get()
                .uri("/api/dash/block/00000000000000427a9048cbb95c484afc559c01bc42eef505e075e5ef05c93f")
                .exchange()
                .expectStatus().isOk()
                .expectBody(BitcoinBlock.class).isEqualTo(expectedBlock)
                // .returnResult();
                .consumeWith(result -> {
                    Assertions.assertIterableEquals(result.getResponseBody().getTxids(), expectedBlock.getTxids());
                });
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

    // @Test
    // public void getBlockchainInfo() {
    //
    //     mockServer
    //             .when(request()
    //                     .withMethod("POST")
    //                     .withBody("{\"jsonrpc\":\"jsonrpc\",\"id\":\"optional_string\",\"method\":\"getblockchaininfo\",\"params\":[]}")
    //             )
    //             .respond(
    //                     response()
    //                             .withBody(DashRPCResponses.getBlockchainInfoResponse)
    //                             .withHeader("Content-Type", "text/html")
    //             );
    //
    //
    //     String res = "{\"chain\":\"main\",\"blocks\":909894,\"headers\":909894,\"bestblockhash\":\"000000000000001707165ebe701845fde191fd2c300e7a05c55107382398bfc4\",\"difficulty\":44463768.24481726,\"mediantime\":1532612865,\"verificationprogress\":0.9999992287526149,\"chainwork\":\"00000000000000000000000000000000000000000000092d21b401da81406d40\",\"pruned\":false,\"softforks\":[{\"id\":\"bip34\",\"version\":2,\"reject\":{\"status\":true}},{\"id\":\"bip66\",\"version\":3,\"reject\":{\"status\":true}},{\"id\":\"bip65\",\"version\":4,\"reject\":{\"status\":true}}],\"bip9_softforks\":{\"csv\":{\"status\":\"active\",\"startTime\":1486252800,\"timeout\":1517788800,\"since\":622944},\"dip0001\":{\"status\":\"active\",\"startTime\":1508025600,\"timeout\":1539561600,\"since\":782208},\"bip147\":{\"status\":\"started\",\"bit\":2,\"startTime\":1524477600,\"timeout\":1556013600,\"since\":858816}}}";
    //
    //     String resScientificNotation = "{\"chain\":\"main\",\"blocks\":909894,\"headers\":909894,\"bestblockhash\":\"000000000000001707165ebe701845fde191fd2c300e7a05c55107382398bfc4\",\"difficulty\":4.446376824481726E7,\"mediantime\":1532612865,\"verificationprogress\":0.9999992287526149,\"chainwork\":\"00000000000000000000000000000000000000000000092d21b401da81406d40\",\"pruned\":false,\"softforks\":[{\"id\":\"bip34\",\"version\":2,\"reject\":{\"status\":true}},{\"id\":\"bip66\",\"version\":3,\"reject\":{\"status\":true}},{\"id\":\"bip65\",\"version\":4,\"reject\":{\"status\":true}}],\"bip9_softforks\":{\"csv\":{\"status\":\"active\",\"startTime\":1486252800,\"timeout\":1517788800,\"since\":622944},\"dip0001\":{\"status\":\"active\",\"startTime\":1508025600,\"timeout\":1539561600,\"since\":782208},\"bip147\":{\"status\":\"started\",\"bit\":2,\"startTime\":1524477600,\"timeout\":1556013600,\"since\":858816}}}";
    //
    //     RPCResult expectedRPCResult = new RPCResult();
    //     expectedRPCResult.setResponse(resScientificNotation);
    //
    //     webTestClient
    //             .get()
    //             .uri("/api/dash/blockchaininfo")
    //             .exchange()
    //             .expectStatus().isOk()
    //             .expectBody(String.class).isEqualTo(resScientificNotation);
    // }


    @Test
    public void getBlockchainInfo() {

        String res = "{\"chain\":\"main\",\"blocks\":909894,\"headers\":909894,\"bestblockhash\":\"000000000000001707165ebe701845fde191fd2c300e7a05c55107382398bfc4\",\"difficulty\":44463768.24481726,\"mediantime\":1532612865,\"verificationprogress\":0.9999992287526149,\"chainwork\":\"00000000000000000000000000000000000000000000092d21b401da81406d40\",\"pruned\":false,\"softforks\":[{\"id\":\"bip34\",\"version\":2,\"reject\":{\"status\":true}},{\"id\":\"bip66\",\"version\":3,\"reject\":{\"status\":true}},{\"id\":\"bip65\",\"version\":4,\"reject\":{\"status\":true}}],\"bip9_softforks\":{\"csv\":{\"status\":\"active\",\"startTime\":1486252800,\"timeout\":1517788800,\"since\":622944},\"dip0001\":{\"status\":\"active\",\"startTime\":1508025600,\"timeout\":1539561600,\"since\":782208},\"bip147\":{\"status\":\"started\",\"bit\":2,\"startTime\":1524477600,\"timeout\":1556013600,\"since\":858816}}}";

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

        BlockchainInfo expectedResult = new BlockchainInfo();
        expectedResult.setBlocks(531489L);


        webTestClient
                .get()
                .uri("/api/dash/blockchaininfo")
                .exchange()
                .expectStatus().isOk()
                .expectBody(BlockchainInfo.class)
                .consumeWith(result -> {
                    Assertions.assertEquals(result.getResponseBody().getBlocks(), 909894L);
                    Assertions.assertEquals(result.getResponseBody().getBestblockhash(), "000000000000001707165ebe701845fde191fd2c300e7a05c55107382398bfc4");
                    // Assertions.assertEquals(result.getResponseBody().getMediantime(), 1532612865L);
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
                                .withBody(DashRPCResponses.getBestBlockHashResponse)
                                .withHeader("Content-Type", "text/html")
                );

        webTestClient
                .get()
                .uri("/api/dash/bestblockhash")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).isEqualTo("000000000000003941fb8b64f23b1dc0391892c87dd8054a1f262b70203b2582");
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
                                .withBody(DashRPCResponses.getMempoolResponse)
                                .withHeader("Content-Type", "text/html")
                );

        TransactionPool expectedPool = new TransactionPool();
        expectedPool.addTransaction("5bc76af67921c657c1c321de16a0403671365c6c07376a814b5de28c02ebe09b");
        expectedPool.addTransaction("22121a969bd36559f38eb4d01850d044b34d5d75d912ba10969e4f64fd345be9");
        expectedPool.addTransaction("24219415fc41d5205c455b3e1aef2b9323c3a2f5bc82eb2d08c1ea4336b3d3e5");
        expectedPool.addTransaction("4607202d56516e4f10af26ada8f210c4802e195e8d43e5ef7f7470d2d0171c9c");

        webTestClient
                .get()
                .uri("/api/dash/transactionpool")
                .exchange()
                .expectStatus().isOk()
                .expectBody(TransactionPool.class).isEqualTo(expectedPool);
                // .expectBody(String.class).isEqualTo(DashRPCResponses.getMempoolResponse);
    }
}