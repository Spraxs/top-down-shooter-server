package nl.jaimyputter.server.websocket.modules.packet.packets.in;

import nl.jaimyputter.server.websocket.modules.packet.framework.PacketId;
import nl.jaimyputter.server.websocket.modules.packet.packets.PacketIn;
import nl.jaimyputter.server.websocket.server.handlers.Client;

import java.io.ByteArrayInputStream;

/**
 * Created by Spraxs
 * Date: 11/5/2019
 */

@PacketId(1)
public class PacketInPing extends PacketIn {

    public PacketInPing(Client client, ByteArrayInputStream bais) {
        super(client, bais);
    }

    public long timeInMillis;


    @Override
    public void onDataHandled() {
        System.out.println("Delay: " + (System.currentTimeMillis() - timeInMillis) + " ms");
    }
}
