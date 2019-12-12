package nl.jaimyputter.server.websocket.modules.packet.packets.out;

import nl.jaimyputter.server.websocket.framework.geometry.Vector2;
import nl.jaimyputter.server.websocket.modules.packet.packets.PacketOut;

/**
 * Created by Spraxs
 * Date: 12/11/2019
 */

public class PacketOutPlayerDeath extends PacketOut {

    public long playerId;

    public double deathPositionX;
    public double deathPositionY;

    public PacketOutPlayerDeath(long playerId, Vector2 deathPosition) {
        this.playerId = playerId;

        this.deathPositionX = deathPosition.getX();
        this.deathPositionY = deathPosition.getY();
    }

    @Override
    public void onDataPrepare() {
        id = 8;
    }
}
