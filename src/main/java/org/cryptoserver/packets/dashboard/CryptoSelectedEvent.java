package org.cryptoserver.packets.dashboard;

import org.cryptoserver.engine.CoinAPIManager;
import org.cryptoserver.packets.Event;
import org.cryptoserver.packets.headers.OutgoingHeaders;
import org.cryptoserver.server.Connection;
import org.json.JSONArray;
import org.json.JSONObject;

public class CryptoSelectedEvent extends Event {
    @Override
    public void handle(Connection connection, JSONObject packet) {
        String cryptocurrencyName = packet.getString("cryptoName");
        String cryptoAssetId = CoinAPIManager.getInstance().getCryptoCurrencies().get(cryptocurrencyName);

        JSONArray data = CoinAPIManager.getInstance().getTimeSeriesData(cryptoAssetId, "EUR", "1HRS", "2024-11-11T00:00:00", "2024-11-18T00:00:00", 1000);

        JSONObject response = new JSONObject();
        response.put("header", OutgoingHeaders.DASHBOARD_SELECT_CRYPTO_RESPONSE);
        response.put("cryptoName", cryptocurrencyName);
        response.put("cryptoData", data);

        connection.sendPacket(response);
    }
}
