package org.mocktcpserver;

import org.mocktcpserver.model.Request;
import org.mocktcpserver.model.Response;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class MockTcpServer implements Closeable {
    private final ServerSocket serverSocket;
    private Request request;
    private Response response;

    @SuppressWarnings("WeakerAccess")
    public MockTcpServer(int portNumber) throws IOException {
        serverSocket = new ServerSocket(portNumber);
        new Thread(() -> {
            try {
                Socket clientSocket = serverSocket.accept();
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    if (inputLine.equals(request.body())) {
                        out.println(response.body());
                    } else {
                        out.println();
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    @SuppressWarnings("WeakerAccess")
    public void end() throws IOException {
        serverSocket.close();
    }

    public MockTcpServer when(Request request, Response response) {
        this.request = request;
        this.response = response;
        return this;
    }

    @Override
    public void close() throws IOException {
        this.end();
    }

    public int getPortNumber() {
        return this.serverSocket.getLocalPort();
    }
}
