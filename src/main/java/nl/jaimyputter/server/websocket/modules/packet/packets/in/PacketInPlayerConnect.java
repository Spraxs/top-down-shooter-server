package nl.jaimyputter.server.websocket.modules.packet.packets.in;

import nl.jaimyputter.server.websocket.framework.managers.IdManager;
import nl.jaimyputter.server.websocket.modules.packet.framework.PacketId;
import nl.jaimyputter.server.websocket.modules.packet.packets.PacketIn;
import nl.jaimyputter.server.websocket.modules.packet.packets.out.PacketOutPlayerConnect;
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

    @Override
    public void onDataHandled() {

        client.channelSend(new PacketOutPlayerConnect(IdManager.getNextId(), client.getAccountName(), 0.0D, 0.0D));
    }
}
