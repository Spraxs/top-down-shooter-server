package nl.jaimyputter.server.websocket.modules.packet.packets.in;

import nl.jaimyputter.server.websocket.Server;
import nl.jaimyputter.server.websocket.framework.geometry.Vector2;
import nl.jaimyputter.server.websocket.modules.packet.PacketModule;
import nl.jaimyputter.server.websocket.modules.packet.framework.PacketId;
import nl.jaimyputter.server.websocket.modules.packet.packets.PacketIn;
import nl.jaimyputter.server.websocket.modules.packet.packets.out.PacketOutDebugCollider;
import nl.jaimyputter.server.websocket.modules.packet.packets.out.PacketOutPlayerPositionChange;
import nl.jaimyputter.server.websocket.modules.world.framework.creatures.Player;
import nl.jaimyputter.server.websocket.server.handlers.Client;

import java.io.ByteArrayInputStream;

/**
 * Created by Spraxs
 * Date: 12/5/2019
 */

@PacketId(4)
public class PacketInPlayerPositionChange extends PacketIn {

    public PacketInPlayerPositionChange(Client client, ByteArrayInputStream bais) {
        super(client, bais);
    }

    public double posX;
    public double posY;

    @Override
    public void onDataHandled() {


        Vector2 newPos = new Vector2((float) posX, (float) posY);

        // TODO Call event and check if cancelled

        Player player = client.getPlayer();

        player.setPosition(newPos);

        /* Debug player hit box points to his client

        player.channelSend(new PacketOutDebugCollider(
                player.getBoxCollider2().getPointA(),
                player.getBoxCollider2().getPointB(),
                player.getBoxCollider2().getPointC(),
                player.getBoxCollider2().getPointD())
        );
         */

        long playerId = player.getObjectId();


        Server.byModule(PacketModule.class).sendPacketToAllClientsExcept(new PacketOutPlayerPositionChange(playerId, newPos), client);
    }
}
