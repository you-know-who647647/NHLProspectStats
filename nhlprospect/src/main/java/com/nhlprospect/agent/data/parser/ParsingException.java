package com.nhlprospect.agent.data.parser;

public class ParsingException extends RuntimeException {

    public ParsingException(Throwable t) {
        super(t);
    }

    public ParsingException(String message) {
        super(message);
    }
}
