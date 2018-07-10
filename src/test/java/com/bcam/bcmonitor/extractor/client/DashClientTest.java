package com.bcam.bcmonitor.extractor.client;

import org.junit.jupiter.api.Test;
import org.mockserver.integration.ClientAndServer;

import java.io.IOException;

class DashClientTest {
    private ClientAndServer mockServer;

    private String userName = "dashuser1";
    // private String password = "Y44evktH7CY9Ilvo";
    private String password = "password";
    private String hostName = "localhost";
    private int port = 9998;

    // @BeforeEach
    // void setUp() {
    // }
    //
    // @AfterEach
    // void tearDown() {
    // }

    @Test
    void getBlock() throws IOException, InterruptedException {
        DashClient dc = new DashClient(userName, password, hostName, port);

        while (true) {
            // System.out.println(dc.getBlock("000007d91d1254d60e2dd1ae580383070a4ddffa4c64c2eeb4a2f9ecc0414343").getDifficulty());
            // System.out.println(dc.getBlockString("000007d91d1254d60e2dd1ae580383070a4ddffa4c64c2eeb4a2f9ecc0414343"));
            // System.out.println(dc.getTransaction("30e60560fe229da7304beba39df9e45d956e0be52a2267b97e292eeeaf35324f"));
            System.out.println(dc.getBlockHash(1));
            // System.out.println(dc.getTransactionPoolInfo());
            Thread.sleep(1000);
        }
    }
}

