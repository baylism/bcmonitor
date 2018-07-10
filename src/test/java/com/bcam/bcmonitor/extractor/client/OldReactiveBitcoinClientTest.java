package com.bcam.bcmonitor.extractor.client;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockserver.integration.ClientAndServer;

import java.io.IOException;

import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

class OldReactiveBitcoinClientTest {
    private ClientAndServer mockServer;
    private String validBlockResponse;
    private String errorBlockResponse;

    private String userName = "bitcoinrpc";
    private String password = "123";
    private String hostName = "localhost";
    private int port = 28332;


    public OldReactiveBitcoinClientTest() {
        validBlockResponse = "{\n" +
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

        errorBlockResponse = "{\n" +
                "\t\"result\": null,\n" +
                "\t\"error\": {\n" +
                "\t\t\"code\": -5,\n" +
                "\t\t\"message\": \"Block not found\"\n" +
                "\t},\n" +
                "\t\"id\": null\n" +
                "}";

    }


    @Before
    public void startServer() {
        mockServer = startClientAndServer(28332);
    }

    @After
    public void stopServer() {
        mockServer.stop();
    }

    //TODO could use .json() instead of .withBody()
    @Test
    public void validRequestBlock() throws IOException {
        mockServer = startClientAndServer(28332);
        mockServer
                .when(
                        request()
                                .withMethod("POST")
                                .withBody("{\"jsonrpc\":\"jsonrpc\",\"id\":\"optional_string\",\"method\":\"getblock\",\"params\":[\"hash\",2]}")
                )
                .respond(
                        response()
                                .withBody(validBlockResponse)
                );

        // ReactiveBitcoinClient bc = new ReactiveBitcoinClient(userName, password, hostName, port);

        // Mono<BitcoinBlock> b = bc.getBlock("hash");
        //
        // b.subscribe();
        // assertEquals("00000000839a8e6886ab5951d76f411475428afc90947ee320161bbf18eb6048", "00000000839a8e6886ab5951d76f411475428afc90947ee320161bbf18eb6048");

    }


}