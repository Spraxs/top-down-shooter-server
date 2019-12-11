package nl.jaimyputter.server.websocket.modules.packet.packets.out;

import nl.jaimyputter.server.websocket.framework.geometry.Vector2;
import nl.jaimyputter.server.websocket.modules.packet.packets.PacketOut;

/**
 * Created by Spraxs
 * Date: 12/5/2019
 */

public class PacketOutPlayerPositionChange extends PacketOut {

    private final Vector2 position;

    public long playerId;

    public double posX;
    public double posY;

    public PacketOutPlayerPositionChange(long playerId, Vector2 position) {
        this.playerId = playerId;
        this.position = position;
    }

    @Override
    public void onDataPrepare() {
        id = 4;

        posX = position.getX();
        posY = position.getY();
    }
}
