package com.bcam.bcmonitor.extractor.rpc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;

/**
 * Base class for RPC requests
 */
public class JSONRPCRequest {
    private String jsonrpc;
    private String id;
    private String method;

    private ObjectNode root;
    private ArrayNode params;

    public JSONRPCRequest(String RPCmethod) {
        jsonrpc = "jsonrpc"; // version no. part of JSONRPC spec but currently ignored by bitcoind
        id = "optional_string";
        method = RPCmethod;

        ObjectMapper mapper = new ObjectMapper();
        params = mapper.createArrayNode();
        root = mapper.createObjectNode();

        root.put("jsonrpc", jsonrpc);
        root.put("id", id);
        root.put("method", method);
    }

    public void addParam(String param) {
        params.add(param);
    }

    public void addParam(int param) {
        params.add(param);
    }

    public void addParam(ArrayList<String> params) {
        params.addAll(params);
    }

    public void addParam(Boolean param) {
        params.add(param);
    }

    @Override
    public String toString() {
        root.putArray("params").addAll(params);
        return root.toString();
    }
}
