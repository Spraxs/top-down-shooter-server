package nl.jaimyputter.server.websocket.modules.packet.packets.out;

import nl.jaimyputter.server.websocket.modules.packet.packets.PacketOut;

/**
 * Created by Spraxs
 * Date: 12/11/2019
 */

public class PacketOutPlayerDeath extends PacketOut {

    public long playerId;

    public PacketOutPlayerDeath(long playerId) {
        this.playerId = playerId;
    }

    @Override
    public void onDataPrepare() {
        id = 8;


    }
}
