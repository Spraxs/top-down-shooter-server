package nl.jaimyputter.server.websocket.modules.packet.packets.out;

import lombok.Getter;
import nl.jaimyputter.server.websocket.modules.packet.packets.PacketOut;

public class PacketOutPlayerConnect extends PacketOut {

    public PacketOutPlayerConnect() {
        super();
        writeShort(0);

        writeInt(5);

        writeDouble(1.0d);
        writeDouble(2.5d);

        writeLong(System.currentTimeMillis());
    }
}
