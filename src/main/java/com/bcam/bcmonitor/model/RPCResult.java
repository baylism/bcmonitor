package com.bcam.bcmonitor.model;

import java.util.Objects;

public class RPCResult {

    private String response;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return response;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RPCResult rpcResult = (RPCResult) o;
        return Objects.equals(response, rpcResult.response);
    }

    @Override
    public int hashCode() {

        return Objects.hash(response);
    }
}
