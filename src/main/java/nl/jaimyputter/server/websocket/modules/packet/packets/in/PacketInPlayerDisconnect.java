package nl.jaimyputter.server.websocket.modules.packet.packets.in;

import nl.jaimyputter.server.websocket.Main;
import nl.jaimyputter.server.websocket.modules.packet.framework.PacketId;
import nl.jaimyputter.server.websocket.modules.packet.packets.PacketIn;
import nl.jaimyputter.server.websocket.modules.world.WorldModule;
import nl.jaimyputter.server.websocket.modules.world.framework.creatures.Player;
import nl.jaimyputter.server.websocket.server.handlers.Client;

import java.io.ByteArrayInputStream;

@PacketId(2)
public class PacketInPlayerDisconnect extends PacketIn {

    public PacketInPlayerDisconnect(Client client, ByteArrayInputStream bais) {
        super(client, bais);
    }

    public double posX;
    public double posY;

    @Override
    public void onDataHandled() {
        WorldModule worldModule = Main.byModule(WorldModule.class);

        Player player = client.getPlayer();

        player.getLocation().setX(posX);
        player.getLocation().setY(posY);

        worldModule.removePlayer(player); // Remove player & send packets
    }
}
