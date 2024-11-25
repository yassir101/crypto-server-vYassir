package org.cryptoserver.packets;

import org.cryptoserver.server.Connection;
import org.json.JSONObject;

public abstract class Event {
    public abstract void handle(Connection connection, JSONObject packet);
}
