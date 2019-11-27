package nl.jaimyputter.server.websocket.modules.packet.packets.in;

import nl.jaimyputter.server.websocket.Main;
import nl.jaimyputter.server.websocket.framework.managers.IdManager;
import nl.jaimyputter.server.websocket.modules.packet.PacketModule;
import nl.jaimyputter.server.websocket.modules.packet.framework.PacketId;
import nl.jaimyputter.server.websocket.modules.packet.packets.PacketIn;
import nl.jaimyputter.server.websocket.modules.packet.packets.out.PacketOutPlayerConnect;
import nl.jaimyputter.server.websocket.modules.packet.packets.out.PacketOutPlayerConnectOwn;
import nl.jaimyputter.server.websocket.server.Server;
import nl.jaimyputter.server.websocket.server.handlers.Client;

import java.io.ByteArrayInputStream;

/**
 * Created by Spraxs
 * Date: 11/1/2019
 */

@PacketId(0)
public class PacketInPlayerConnect extends PacketIn {

    public PacketInPlayerConnect(Client client, ByteArrayInputStream bais) {
        super(client, bais);
    }

    public String playerName;

    @Override
    public void onDataHandled() {

        client.setAccountName(playerName); // Set client name

        long playerId = IdManager.getNextId();

        double x = 0.0D;
        double y = 0.0D;

        client.setPlayerId(playerId);

        client.channelSend(new PacketOutPlayerConnectOwn(playerId, playerName, x, y));

        Main.byModule(PacketModule.class).sendPacketToAllClientsExcept(new PacketOutPlayerConnect(playerId, playerName, x, y), client);

        Server.getOnlineClients().stream().filter(c -> c.getPlayerId() != client.getPlayerId())
                .forEach(c -> client.channelSend(new PacketOutPlayerConnect(c.getPlayerId(), c.getAccountName(), x, y)));
    }
}
 