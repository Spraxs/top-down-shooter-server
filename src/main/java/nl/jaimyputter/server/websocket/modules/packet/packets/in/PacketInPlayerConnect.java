package nl.jaimyputter.server.websocket.modules.packet.packets.in;

import nl.jaimyputter.server.websocket.modules.packet.framework.PacketId;
import nl.jaimyputter.server.websocket.modules.packet.packets.PacketIn;

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
        System.out.println("level: " + level);
        System.out.println("speed: " + speed);
        System.out.println("damage: " + damage);

        System.out.println("Time: " + (System.currentTimeMillis() - timeSendMillis));
    }
}
