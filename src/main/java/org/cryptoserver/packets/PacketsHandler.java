package org.cryptoserver.packets;

import org.cryptoserver.packets.authentication.LoginEvent;
import org.cryptoserver.packets.dashboard.CryptoSelectedEvent;
import org.cryptoserver.packets.headers.IncomingHeaders;
import org.cryptoserver.server.Connection;
import org.json.JSONObject;

import java.util.HashMap;

public class PacketsHandler {
    private final HashMap<Integer, Event> events;
    private static PacketsHandler instance;

    public PacketsHandler() {
        this.events = new HashMap<>();
        this.registerEvents();
    }

    public void registerEvents() {
        this.getEvents().put(IncomingHeaders.LOGIN_SUBMIT_EVENT, new LoginEvent());
        this.getEvents().put(IncomingHeaders.DASHBOARD_SELECT_CRYPTO_EVENT, new CryptoSelectedEvent());
    }

    public static PacketsHandler getInstance() {
        if (instance == null) {
            instance = new PacketsHandler();
            System.out.println("Packets handler loaded!");
        }

        return instance;
    }

    public HashMap<Integer, Event> getEvents() {
        return this.events;
    }

    public void handle(Connection connection, JSONObject packet) {
        if (packet.has("header")) {
            int header = packet.getInt("header");
            if (this.getEvents().containsKey(header)) {
                // Trigger the event corresponding to this header
                this.getEvents().get(header).handle(connection, packet);
            } else {
                connection.log("Unregistered event header");
            }
        } else {
            connection.log("Invalid packet format");
        }
    }
}
