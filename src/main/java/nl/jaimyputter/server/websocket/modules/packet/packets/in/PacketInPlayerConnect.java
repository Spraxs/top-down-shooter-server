package nl.jaimyputter.server.websocket.modules.packet.packets.in;

import nl.jaimyputter.server.websocket.modules.packet.framework.PacketId;
import nl.jaimyputter.server.websocket.modules.packet.packets.PacketIn;

/**
 * Created by Spraxs
 * Date: 11/1/2019
 */

@PacketId(0)
public class PacketInPlayerConnect extends PacketIn {

    public int level;

    public float speed;

    public double damage;

    public PacketInPlayerConnect(byte[] bytes) {
        super(bytes);

        System.out.println(level);
    }
}
