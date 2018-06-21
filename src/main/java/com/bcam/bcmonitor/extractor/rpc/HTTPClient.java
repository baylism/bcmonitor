package com.bcam.bcmonitor.extractor.rpc;


import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;

/**
 * Makes requests and calls handler
 * Responsible for error handling
 *
 * annotation: http://www.baeldung.com/spring-value-annotation
 *
 * http://hc.apache.org/httpcomponents-client-ga/httpclient/examples/org/apache/http/examples/client/ClientWithResponseHandler.java
 *
 * http://hc.apache.org/httpcomponents-client-ga/tutorial/html/fundamentals.html
 *
 * rpcuser=bitcoinrpc
 * rpcpassword=2dbccb365c9f654af04d4ac648dfef4fdyn3160-82
 */
public class HTTPClient {

    private String userName;
    private String password;

    private CloseableHttpClient httpClient;
    private HttpClientContext context;
    private HttpHost targetHost;
    private CredentialsProvider credsProvider;


    public HTTPClient(String hostName, int port, String userName, String password) {
        this.userName = userName;
        this.password = password;

        httpClient = HttpClients.createDefault();
        context = new HttpClientContext();

        targetHost = new HttpHost(hostName, port, "http");

        credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(
                new AuthScope(targetHost.getHostName(), targetHost.getPort()),
                new UsernamePasswordCredentials(userName, password));

        context.setCredentialsProvider(credsProvider);
    }

    // TODO reafactor to take a JSONRPCRequest object instead of a String?
    public String request(String JSONRequest) throws IOException {
        HttpPost request = new HttpPost();
        HTTPResponseHandler handler = new HTTPResponseHandler();

        request.setEntity(new StringEntity(JSONRequest, ContentType.APPLICATION_JSON));

        return httpClient.execute(targetHost, request, handler, context);
    }


}
