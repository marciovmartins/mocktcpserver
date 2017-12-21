package org.mocktcpserver.model;

public class Response {
    private String data;

    public static Response response() {
        return new Response();
    }

    public Response withBody(String data) {
        this.data = data;
        return this;
    }

    public String body() {
        return this.data;
    }
}
