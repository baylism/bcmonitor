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
    private ObjectMapper mapper;

    private ObjectNode jsonParams;
    private Boolean enableJsonParams;

    public JSONRPCRequest(String RPCmethod) {
        jsonrpc = "jsonrpc"; // version no. part of JSONRPC spec but currently ignored by bitcoind
        id = "optional_string";
        method = RPCmethod;

        mapper = new ObjectMapper();
        params = mapper.createArrayNode();
        root = mapper.createObjectNode();

        root.put("jsonrpc", jsonrpc);
        root.put("id", id);
        root.put("method", method);


        jsonParams = mapper.createObjectNode();
        enableJsonParams = Boolean.FALSE;

    }

    public void addJsonParam(String name, String param) {

        jsonParams.put(name, param);
        enableJsonParams = Boolean.TRUE;

    }

    public void addParam(String param) {
        params.add(param);
    }

    public void addParam(int param) {
        params.add(param);
    }

    public void addParam(long param) {
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

        if (enableJsonParams) {
            root.set("params", jsonParams);
        } else {
            root.putArray("params").addAll(params);
        }
        return root.toString();
    }
}
