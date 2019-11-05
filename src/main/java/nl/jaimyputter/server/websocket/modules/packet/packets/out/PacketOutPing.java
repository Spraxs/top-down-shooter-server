package nl.jaimyputter.server.websocket.modules.packet.packets.out;

import nl.jaimyputter.server.websocket.modules.packet.packets.PacketOut;

/**
 * Created by Spraxs
 * Date: 11/5/2019
 */

public class PacketOutPing extends PacketOut {

    public long timeInMillis;

    @Override
    public void onDataPrepare() {
        id = 1;
        timeInMillis = System.currentTimeMillis();
    }
}
