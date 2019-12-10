package nl.jaimyputter.server.websocket.modules.packet.packets.in;

import nl.jaimyputter.server.websocket.modules.packet.PacketModule;
import nl.jaimyputter.server.websocket.modules.packet.framework.PacketId;
import nl.jaimyputter.server.websocket.modules.packet.packets.PacketIn;
import nl.jaimyputter.server.websocket.modules.packet.packets.out.PacketOutPlayerPositionChange;
import nl.jaimyputter.server.websocket.modules.world.framework.utils.Transform;
import nl.jaimyputter.server.websocket.Server;
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
        Transform location = client.getPlayer().getLocation();

        // TODO Call event and check if cancelled

        location.setX(posX);
        location.setY(posY);

        Server.byModule(PacketModule.class).sendPacketToAllClientsExcept(new PacketOutPlayerPositionChange(client.getPlayer()), client);


    }
}
