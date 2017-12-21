package org.mocktcpserver.model;

public class Request {
    private String data;

    public static Request request() {
        return new Request();
    }

    public Request withBody(String data) {
        this.data = data;
        return this;
    }

    public String body() {
        return this.data;
    }
}
