package com.bcam.bcmonitor.extractor.client;


import com.bcam.bcmonitor.extractor.mapper.*;
import com.bcam.bcmonitor.extractor.rpc.JSONRPCRequest;
import com.bcam.bcmonitor.extractor.rpc.ReactiveHTTPClient;
import com.bcam.bcmonitor.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;

// @Qualifier("ReactiveDashClient")
@Component
public class ReactiveMoneroClient extends ReactiveClientImpl implements ReactiveClient<MoneroBlock, MoneroTransaction>, BlockchainInfoClient {


    @Value("${MONERO_HOSTNAME}")
    private String hostName;

    @Value("${MONERO_PORT}")
    private int port;

    @Value("${MONERO_UN}")
    private String userName;

    @Value("${MONERO_PW}")
    private String password;

    public ReactiveMoneroClient() {
        super();
    }

    @PostConstruct
    protected void buildClient() {
        System.out.println("Building Monero client with hostname " + hostName);

        ObjectMapper mapper = buildMapper();

        client = new ReactiveHTTPClient(hostName, port, userName, password, mapper);
    }

    @Override
    ObjectMapper buildMapper() {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();

        module.addDeserializer(TransactionPoolInfo.class, new BitcoinTransactionPoolInfoDeserializer());
        module.addDeserializer(TransactionPool.class, new BitcoinTransactionPoolDeserializer());
        module.addDeserializer(BlockchainInfo.class, new MoneroBlockchainInfoDeserializer());
        module.addDeserializer(RPCResult.class, new RPCResultDeserializer());

        // Monero
        module.addDeserializer(MoneroBlock.class, new MoneroBlockDeserializer());
        module.addDeserializer(MoneroTransaction.class, new MoneroTransactionDeserializer());

        mapper.registerModule(module);

        return mapper;
    }

    @Override
    ReactiveHTTPClient getClient() {
        return client;
    }

    @Override
    public Mono<MoneroBlock> getBlock(String hash) {
        JSONRPCRequest request = new JSONRPCRequest("get_block");
        // request.addParam(hash);
        // request.addParam("{hash:" + hash + "}");
        request.addJsonParam("hash", hash);

        return client
                .requestResponseSpec(request.toString())
                .bodyToMono(MoneroBlock.class);
    }


    @Override
    public Mono<MoneroTransaction> getTransaction(String hash) {

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode root = mapper.createObjectNode();
        ArrayNode hashesNode = mapper.createArrayNode();

        hashesNode.add(hash);

        // ArrayList<String> hashes = new ArrayList<>();
        // hashes.add(hash);

        root.putArray("txs_hashes").addAll(hashesNode);


        return client
                .requestResponseSpecURI("get_transaction", root.toString())
                .bodyToMono(MoneroTransaction.class);

    }

    @Override
    public Mono<BlockchainInfo> getBlockchainInfo() {


        JSONRPCRequest request = new JSONRPCRequest("get_info");

        return getClient()
                .requestResponseSpecURI("ison_rpc", request.toString())
                .bodyToMono(BlockchainInfo.class);

    }

    @Override
    public Mono<String> getBestBlockHash() {

        return getBlockchainInfo().map(BlockchainInfo::getBestblockhash);
    }
}

