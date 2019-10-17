package nl.jaimyputter.server.modules.network.packets.sendable;

import nl.jaimyputter.server.modules.network.packets.SendablePacket;

/**
 * Created by Spraxs
 * Date: 10/17/2019
 */

//TODO Fix ByteArrayOutputStream to C#

public class PingClient extends SendablePacket {

    public PingClient() {
        writeShort(0);

        long time = System.currentTimeMillis();

        System.out.println(time);

        writeLong(time);
    }
}
