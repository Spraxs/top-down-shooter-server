package nl.jaimyputter.server.websocket.server;

import nl.jaimyputter.server.websocket.server.handlers.Client;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Spraxs
 * Date: 10/29/2019
 */

public class Server {

    private static List<Client> ONLINE_CLIENTS = new CopyOnWriteArrayList<>();

    public static Collection<Client> getOnlineClients() {
        return ONLINE_CLIENTS;
    }

    public static void addClient(Client client) {
        if (!ONLINE_CLIENTS.contains(client)) {
            ONLINE_CLIENTS.add(client);
        }
    }

    public static void removeClient(Client client) {
        ONLINE_CLIENTS.remove(client);
    }
}
