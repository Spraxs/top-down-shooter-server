package nl.jaimyputter.server.websocket.modules.packet.packets.in;

import nl.jaimyputter.server.websocket.Main;
import nl.jaimyputter.server.websocket.modules.packet.PacketModule;
import nl.jaimyputter.server.websocket.modules.packet.framework.PacketId;
import nl.jaimyputter.server.websocket.modules.packet.packets.PacketIn;
import nl.jaimyputter.server.websocket.modules.packet.packets.out.PacketOutPing;
import nl.jaimyputter.server.websocket.modules.packet.packets.out.PacketOutPlayerConnect;

import java.io.ByteArrayInputStream;

/**
 * Created by Spraxs
 * Date: 11/1/2019
 */

@PacketId(0)
public class PacketInPlayerConnect extends PacketIn {

    public int level;

    public double speed;

    public double damage;

    public long timeSendMillis;

    public PacketInPlayerConnect(ByteArrayInputStream bytes) {
        super(bytes);
    }

    @Override
    public void onDataHandled() {
        Main.byModule(PacketModule.class).sendPacketToAllClients(new PacketOutPing());
    }
}
