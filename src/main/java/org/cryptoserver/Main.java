package org.cryptoserver;

import org.cryptoserver.engine.CoinAPIManager;
import org.cryptoserver.packets.PacketsHandler;
import org.cryptoserver.server.Server;
import org.cryptoserver.storage.Database;
import org.cryptoserver.users.UserManager;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        System.out.println("The server is starting!");

        Database.getInstance();
        UserManager.getInstance();
        PacketsHandler.getInstance();
        CoinAPIManager.getInstance();

        System.out.println("Database & all managers are loaded!");

        try (Server server = new Server(30000)) {
            server.listenClientConnection();
        } catch (IOException e) {
            System.out.println("Server could not be started!");
        }
        //testAPI();
    }

    public static void testAPI() {
        System.out.println("Hello World!");
        System.out.println();
        //JSONArray data = CoinAPIManager.getInstance().getTimeSeriesData("ETH", "EUR", "1DAY", "2024-01-01T00:00:00", "2024-11-05T00:00:00", 365);
        //System.out.println(CoinAPIManager.getInstance().getCryptocurrencies());
        //JSONArray data = CoinAPIManager.getInstance().getTimeSeriesData2("BTC", "EUR", "1DAY", "2024-01-01T00:00:00", "2024-11-05T00:00:00", 365);
//        for (int i = 0; i < data.length(); i++) {
//            System.out.println(data.optString(i));
//        }
    }
}
