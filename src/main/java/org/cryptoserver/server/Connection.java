package org.cryptoserver.server;

import org.cryptoserver.packets.PacketsHandler;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Connection implements Runnable {

    // A socket is an endpoint for communication between two machines.
    private Socket socket;

    private final BufferedReader reader;
    private final PrintWriter writer;

    public Connection(Socket socket) throws IOException {
        this.socket = socket;
        this.reader = new BufferedReader(new InputStreamReader(this.getSocket().getInputStream()));
        this.writer = new PrintWriter(this.getSocket().getOutputStream(), true);
    }

    @Override
    public void run() {
        // Continuous listening of incoming data
        this.listenMessages();
        // if the connection can't listen anymore, then the connection has to be closed
        this.closeConnection();
    }

    public void log(String message) {
        System.out.println("[" + this.getSocket().getRemoteSocketAddress() + "] " + message);
    }

    /**
     * Handles messages which are incoming data
     */
    public void listenMessages() {
        String message;
        try {
            while ((message = this.getReader().readLine()) != null) {
                this.onPacket(this.getParsedMessage(message));
            }
        } catch (IOException e) {
            this.log("Can not read the received message");
        }
    }

    /**
     * Parses the string message into a JSONObject
     * @param message : incoming message, which is not null
     * @return
     */
    public JSONObject getParsedMessage(String message) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(message);
        } catch (JSONException e) {
            this.log("JSON Exception: can not parse the message");
        }
        return jsonObject;
    }

    /**
     * Close the reader, writer and the socket
     */
    public void closeConnection() {
        try {
            this.getReader().close();
            this.getWriter().close();
            this.getSocket().close();
            this.log("Connection closed.");
        } catch (IOException e) {
            this.log("Can not close the connection.");
        }
    }

    public BufferedReader getReader() {
        return this.reader;
    }

    public PrintWriter getWriter() {
        return this.writer;
    }

    public Socket getSocket() {
        return this.socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    /**
     * Send a packet to the client (the packet has to contain a header to be read by the client)
     * @param packet:
     */
    public void sendPacket(JSONObject packet) {
        String message = packet.toString();
        this.getWriter().println(message);
        this.log("Server sends: " + message);
    }

    /**
     * Handles a packet which is an incoming data parsed into a JSONObject
     * @param jsonObject: collected data
     */
    public void onPacket(JSONObject jsonObject) {
        if (jsonObject != null) {
            this.log("Client sends: " + jsonObject);
            PacketsHandler.getInstance().handle(this, jsonObject);
        } else {
            this.log("The incoming packet is null.");
        }
    }
}
