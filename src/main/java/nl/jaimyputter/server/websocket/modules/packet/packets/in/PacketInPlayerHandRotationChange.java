package nl.jaimyputter.server.websocket.modules.packet.packets.in;

import nl.jaimyputter.server.websocket.Server;
import nl.jaimyputter.server.websocket.framework.geometry.Vector2;
import nl.jaimyputter.server.websocket.modules.packet.PacketModule;
import nl.jaimyputter.server.websocket.modules.packet.framework.PacketId;
import nl.jaimyputter.server.websocket.modules.packet.packets.PacketIn;
import nl.jaimyputter.server.websocket.modules.packet.packets.out.PacketOutPlayerHandRotationChange;
import nl.jaimyputter.server.websocket.modules.world.framework.creatures.Player;
import nl.jaimyputter.server.websocket.modules.world.framework.utils.Transform;
import nl.jaimyputter.server.websocket.server.handlers.Client;

import java.io.ByteArrayInputStream;

@PacketId(7)
public class PacketInPlayerHandRotationChange extends PacketIn {

    public PacketInPlayerHandRotationChange(Client client, ByteArrayInputStream bais) {
        super(client, bais);
    }

    public double posX;
    public double posY;

    public double rotZ;

    public double scaleX;

    @Override
    public void onDataHandled() {

        Player player = client.getPlayer();

        Transform transform = player.getHandTransform();

        if (transform == null) transform = new Transform();

        transform.setPosition(new Vector2((float) posX, (float) posY));

        player.setHandTransform(transform);

        long playerId = player.getObjectId();

        Server.byModule(PacketModule.class).sendPacketToAllClientsExcept(new PacketOutPlayerHandRotationChange(playerId, transform.getPosition(), rotZ, scaleX), client);
    }
}
