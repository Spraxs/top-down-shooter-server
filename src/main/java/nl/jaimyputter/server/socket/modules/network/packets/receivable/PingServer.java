package nl.jaimyputter.server.socket.modules.network.packets.receivable;

import nl.jaimyputter.server.socket.modules.network.client.Client;
import nl.jaimyputter.server.socket.modules.network.packets.ReceivablePacket;

/**
 * Created by Spraxs
 * Date: 10/17/2019
 */

public class PingServer {

    //TODO Fix ByteArrayInputStream to C#
    public PingServer(Client client, ReceivablePacket packet) {
        long time = packet.readLong();

        System.out.println(time);

        System.out.println("Time: " + ((double) (System.currentTimeMillis() - time) / 1000));
    }

}
