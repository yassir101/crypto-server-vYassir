package org.cryptoserver.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server extends ServerSocket {

    private final List<Connection> connections = new ArrayList<>();
    private boolean running;

    public Server(int port) throws IOException {
        super(port);
        this.running = true;
        System.out.println("Server started on port " + port + "\n");
    }

    public boolean isRunning() {
        return this.running;
    }

    public void stopServer() {
        this.running = false;
    }

    public List<Connection> getConnections() {
        return this.connections;
    }

    public void listenClientConnection() throws IOException {
        while (this.isRunning()) {
            System.out.println("[Server] Waiting for a new client connection...");
            Socket clientSocket = this.accept();
            System.out.println("[Server] Accepted connection from " + clientSocket.getRemoteSocketAddress());

            // Create a connection object and add it to the list of all current connections
            Connection connection = new Connection(clientSocket);
            this.getConnections().add(connection);
            new Thread(connection).start();
        }
        System.out.println("[Server] Shutting down server, bye!");
    }
}
