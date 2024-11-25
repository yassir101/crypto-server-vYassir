package org.cryptoserver.packets.authentication;

import org.cryptoserver.engine.CoinAPIManager;
import org.cryptoserver.packets.Event;
import org.cryptoserver.packets.headers.OutgoingHeaders;
import org.cryptoserver.server.Connection;
import org.cryptoserver.users.User;
import org.cryptoserver.users.UserManager;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class LoginEvent extends Event {
    @Override
    public void handle(Connection connection, JSONObject packet) {
        String username = packet.getString("username");
        String password = packet.getString("password");
        JSONObject response = new JSONObject();
        response.put("header", OutgoingHeaders.LOGIN_RESPONSE);

        if (UserManager.getInstance().attemptLogin(username, password)) {
            User user = UserManager.getInstance().getUserByUsername(username);
            if (user != null) {
                response.put("loginSuccess", true);
                Collection<String> cryptoNames = CoinAPIManager.getInstance().getCryptoCurrencies().keySet();

                // Sort the list
                List<String> names = new ArrayList<>(cryptoNames);
                Collections.sort(names);

                response.put("cryptoNames", names);
            }
        } else {
            response.put("loginSuccess", false);
        }

        connection.sendPacket(response);
    }
}
