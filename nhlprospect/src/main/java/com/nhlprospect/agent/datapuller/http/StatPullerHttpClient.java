package com.nhlprospect.agent.datapuller.http;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Objects;

public class StatPullerHttpClient {

    private CloseableHttpClient client;

    private static final Header ACCEPT = new BasicHeader("Accept", "application/json");

    public StatPullerHttpClient(CloseableHttpClient client) {
        this.client = Objects.requireNonNull(client);
    }

    public String executeGet(String url) {
        HttpGet getRequest = new HttpGet(url);
        getRequest.addHeader(ACCEPT);
        try (CloseableHttpResponse response = client.execute(getRequest)){
            HttpEntity entity = response.getEntity();
            String output = EntityUtils.toString(entity);
            EntityUtils.consume(entity);
            int status = response.getStatusLine().getStatusCode();
            if(status < 200 || status >= 300) {
                String error = String.format("Found unexpected status code %d when hitting %s. Full response: %s", status, getRequest.toString(), output);
                throw new HttpRequestException(error);
            }
            return output;
        } catch (IOException e) {
            throw new HttpRequestException(e);
        }
    }
}
