package com.bcam.bcmonitor.extractor.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.math.BigDecimal;
import java.math.BigInteger;

public abstract class BlockchainDeserializer<T> extends StdDeserializer<T> {

    public BlockchainDeserializer(Class<?> vc) {
        super(vc);
    }

    public BlockchainDeserializer() {this(null); }

    BigInteger hexNodeToBigInt(JsonNode node) {
        return new BigInteger(node.textValue().substring(2), 16);
    }

    BigDecimal hexNodeToBigDecimal(JsonNode node) {
        return new BigDecimal(node.textValue().substring(2));
    }

    long hexNodeToLong(JsonNode node) {
        return Long.decode(node.textValue());
    }

    void verifyResponse (JsonNode node) {
        if (! node.get("error").isNull()) {
            throw new RPCResponseException("Error received from RPC. Message: " + node.get("error").get("message").textValue());
        }
    }

}
