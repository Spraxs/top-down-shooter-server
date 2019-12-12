package nl.jaimyputter.server.websocket.modules.packet.packets.out;

import nl.jaimyputter.server.websocket.framework.geometry.Vector2;
import nl.jaimyputter.server.websocket.modules.packet.packets.PacketOut;

public class PacketOutPlayerRespawn extends PacketOut {

    public long playerId;

    public double respawnPositionX;
    public double respawnPositionY;

    public PacketOutPlayerRespawn(long playerId, Vector2 respawnPosition) {
        this.playerId = playerId;

        this.respawnPositionX = respawnPosition.getX();
        this.respawnPositionY = respawnPosition.getY();
    }

    @Override
    public void onDataPrepare() {
        id = 9;
    }
}
