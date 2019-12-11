package nl.jaimyputter.server.websocket.modules.packet.packets.in;

import nl.jaimyputter.server.websocket.framework.geometry.Vector2;
import nl.jaimyputter.server.websocket.modules.packet.PacketModule;
import nl.jaimyputter.server.websocket.modules.packet.framework.PacketId;
import nl.jaimyputter.server.websocket.modules.packet.packets.PacketIn;
import nl.jaimyputter.server.websocket.modules.packet.packets.out.PacketOutPlayerConnect;
import nl.jaimyputter.server.websocket.modules.packet.packets.out.PacketOutPlayerConnectOwn;
import nl.jaimyputter.server.websocket.modules.world.WorldModule;
import nl.jaimyputter.server.websocket.modules.world.framework.creatures.Player;
import nl.jaimyputter.server.websocket.Server;
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

    public double sizeX;
    public double sizeY;

    @Override
    public void onDataHandled() {
        Server.addClient(client);
        client.setAccountName(playerName); // Set client name

        WorldModule worldModule = Server.byModule(WorldModule.class);

        Vector2 position = Vector2.zero();

        Vector2 colliderOffset = Vector2.zero();
        Vector2 colliderSize = new Vector2((float) sizeX, (float) sizeY);

        Player player = worldModule.createPlayer(client, client.getAccountName(), position, colliderOffset, colliderSize); // Create player & send packets

        client.setPlayer(player);

        // Send packet to all clients except yourself
        client.channelSend(new PacketOutPlayerConnectOwn(player.getObjectId(), client.getAccountName(), player.getTransform().getPosition().getX(), player.getTransform().getPosition().getY()));

        Server.byModule(PacketModule.class).sendPacketToAllClientsExcept(new PacketOutPlayerConnect(player.getObjectId(),
                client.getAccountName(), player.getTransform().getPosition().getX(), player.getTransform().getPosition().getY()), client);

        Server.getOnlineClients().forEach(c -> System.out.println(c.getPlayer().getObjectId()));

        Server.getOnlineClients().stream().filter(c -> c.getPlayer().getObjectId() != client.getPlayer().getObjectId())
                .forEach(c -> {
                    Player p = c.getPlayer();
                    client.channelSend(new PacketOutPlayerConnect(p.getObjectId(), c.getAccountName(), p.getTransform().getPosition().getX(), p.getTransform().getPosition().getY()));
                });
    }
}
