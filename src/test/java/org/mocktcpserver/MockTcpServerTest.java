package org.mocktcpserver;

import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import static org.mocktcpserver.model.Request.request;
import static org.mocktcpserver.model.Response.response;

public class MockTcpServerTest {
    private static final String hostName = "0.0.0.0";
    private static final int portNumber = 0;

    @Test
    public void testSetupSimpleRequest() throws Exception {
        // setup
        String requestData = "hello";
        String responseData = "world";

        // executions
        MockTcpServer server = new MockTcpServer(portNumber)
                .when(request().withBody(requestData))
                .respond(response().withBody(responseData));

        Socket socket = new Socket(hostName, server.getPortNumber());
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out.println(requestData);

        // assertions
        Assert.assertEquals(responseData, in.readLine());
    }

    @Test
    public void testMissSimpleRequest() throws Exception {
        // setup
        String requestData = "hello";
        String wrongRequestData = "john doe";
        String responseData = "world";
        String wrongResponseData = "";

        // executions
        MockTcpServer server = new MockTcpServer(portNumber)
                .when(request().withBody(requestData))
                .respond(response().withBody(responseData));

        Socket socket = new Socket(hostName, server.getPortNumber());
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out.println(wrongRequestData);

        // assertions
        Assert.assertEquals(wrongResponseData, in.readLine());
    }
}
