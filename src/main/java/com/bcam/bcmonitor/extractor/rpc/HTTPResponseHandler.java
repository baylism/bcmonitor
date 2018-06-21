package com.bcam.bcmonitor.extractor.rpc;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;


import java.io.IOException;


/**
 * Takes HTTP response, handles errors and returns an InputStream
 *
 * Notes:
 * Handleresponse must return value of type ResponseHandler<T>
 *
 */
public class HTTPResponseHandler implements ResponseHandler<String> {

    @Override
    public String handleResponse(HttpResponse response) throws IOException {

        // TODO add better error handling
        StatusLine statusLine = response.getStatusLine();
        HttpEntity entity = response.getEntity();
        if (statusLine.getStatusCode() >= 300) {
            throw new HttpResponseException(
                    statusLine.getStatusCode(),
                    statusLine.getReasonPhrase());
        }
        if (entity == null) {
            throw new ClientProtocolException("Response contains no content");
        }

        return EntityUtils.toString(entity);

    }
}
