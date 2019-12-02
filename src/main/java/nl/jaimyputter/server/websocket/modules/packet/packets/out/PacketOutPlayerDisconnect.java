package nl.jaimyputter.server.websocket.modules.packet.packets.out;

import nl.jaimyputter.server.websocket.modules.packet.packets.PacketOut;

public class PacketOutPlayerDisconnect extends PacketOut {

    public long playerId;

    public PacketOutPlayerDisconnect(long playerId) {
        this.playerId = playerId;
    }

    @Override
    public void onDataPrepare() {
        id = 3;
    }
}
