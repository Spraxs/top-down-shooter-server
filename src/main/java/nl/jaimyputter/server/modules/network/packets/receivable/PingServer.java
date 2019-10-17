package nl.jaimyputter.server.modules.network.packets.receivable;

import nl.jaimyputter.server.modules.network.client.Client;
import nl.jaimyputter.server.modules.network.packets.ReceivablePacket;

/**
 * Created by Spraxs
 * Date: 10/17/2019
 */

public class PingServer {

    //TODO Fix ByteArrayInputStream to C#
    public PingServer(Client client, ReceivablePacket packet) {
        final long beginTime = packet.readLong();

        final long endTime = System.currentTimeMillis();

        System.out.println("BT: " + beginTime + " ET: " + endTime
                + " Total: " + (endTime - beginTime));

        final long ms;
        final float sec;

        ms = (endTime - beginTime);
        sec = ms / 1000.0f;
        System.out.format("%.3f", sec);
        System.out.println("");
    }

}
