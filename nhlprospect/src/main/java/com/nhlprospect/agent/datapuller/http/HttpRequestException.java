package com.nhlprospect.agent.datapuller.http;

public class HttpRequestException extends RuntimeException {

    public HttpRequestException(Throwable t) {
        super(t);
    }

    public HttpRequestException(String message) {
        super(message);
    }
}
